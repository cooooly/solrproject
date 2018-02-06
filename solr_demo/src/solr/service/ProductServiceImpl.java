package solr.service;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;
import solr.dao.ProductQuery;
import solr.po.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cooooly on 2018/2/4.
 */
@Service
public class ProductServiceImpl implements ProductService {
    private String solrUrl = "http://localhost:8080/solr/collection1";
    @Override
    public List<Product> searchProducts(ProductQuery productQuery) throws SolrServerException {

        //进行参数的合法性校验
        if(productQuery==null){
            productQuery = new ProductQuery();
        }
        /*如果当前页为空,指定第一页*/
        if(productQuery.getCurPage()==null){
            productQuery.setCurPage(1);
        }
        /*设置一页显示16条记录*/
        if(productQuery.getRows()==null){
            productQuery.setRows(16);
        }

        //创建solr服务对象
        SolrServer server = new HttpSolrServer(solrUrl);
        //创建查询对象
        SolrQuery query = new SolrQuery();



        /*添加查询条件*/

        /*1-->页面输入查询  关键字  为空, 查询所有 , 否则查询关键字搜索*/
        if(productQuery.getKeywords()==null||productQuery.getKeywords().equals("")){
            query.set("q","*:*");
        }else{
            query.set("q","product_keywords:"+ productQuery.getKeywords());
        }
        /*2-->商品分类搜索*/
        if(productQuery.getCatalog_name()!=null&&!productQuery.getCatalog_name().equals("")){
            query.add("fq","product_catalog_name:"+ productQuery.getCatalog_name());
        }
        /*3-->价格差价范围查询 [x To x]*/
        String price_filterPrice = null;
        if(productQuery.getPrice_start()!=null){
            price_filterPrice = "product_price:["+productQuery.getPrice_start();
            if(productQuery.getPrice_end()!=null){
                price_filterPrice += " TO " + productQuery.getPrice_end()+"]";
            }else{
                price_filterPrice += " TO *]";
            }
        }
        if(price_filterPrice!=null){ //表示价格查询范围
            query.add("fq",price_filterPrice);
        }
        //价格排序
        if(productQuery.getSortField()!=null&&!productQuery.getSortField().equals("")){
            if(productQuery.getSortField()!=null&&productQuery.getSortField().equals("desc")){
                query.addSort(productQuery.getSortField(), SolrQuery.ORDER.desc);
            }else if(productQuery.getSortField()!=null&&productQuery.getSortField().equals("asc")){
                query.addSort(productQuery.getSortField(), SolrQuery.ORDER.asc);
            }
        }

        /*4-->设置 分页参数*/
        int start = productQuery.getRows()*(productQuery.getCurPage()-1);//确认记录从哪一条开始
        query.setStart(start);
        query.setRows(productQuery.getRows());

        /*查询关键字高亮显示*/
        query.setHighlight(true);
        /*设置高亮参数*/
        query.addHighlightField("product_name");
        /*设置前后缀*/
        query.setHighlightSimplePre("<span style='color:red'>");
        query.setHighlightSimplePost("</span>");




        //执行查询
        QueryResponse response = server.query(query);
        //从响应中拿到结果
        SolrDocumentList documents = response.getResults();


        /*匹配到的总记录数*/
        long numFound = documents.getNumFound();
        productQuery.setRecordCount(numFound);
        System.out.println("匹配的总记录数:"+numFound);

        /*计算总页数*/
        int pageCount = (int) Math.ceil(numFound*1.0/productQuery.getRows());
        productQuery.setPageCount(pageCount);

        /*从响应中获取高亮信息*/
        Map<String,Map<String,List<String>>> highlighting = response.getHighlighting();




        //遍历documents , 拿到每个document , 将filed域product属性对应,存到product , product存储到List<Product>集合
        List<Product> products = new ArrayList<Product>();
        for (SolrDocument document : documents) {
            Product product_index = new Product();

            //商品信息id
            if(document.get("id")!=null){
                product_index.setPid(Integer.parseInt(document.get("id").toString()));
            }
            //商品名称
            if(document.get("product_name")!=null){
                product_index.setName(document.get("product_name").toString());
            }
            //商品价格
            if(document.get("product_price")!=null){
                product_index.setPrice(Double.parseDouble(document.get("product_price").toString()));
            }
            //商品分类名称
            if(document.get("product_catalog_name")!=null){
                product_index.setCatalog_name(document.get("product_catalog_name").toString());
            }
            //商品图片
            if(document.get("product_picture")!=null){
                product_index.setPicture(document.get("product_picture").toString());
            }

            /*获取高亮信息*/
            if(highlighting!=null){
                Map<String,List<String>> map = highlighting.get(document.get("id"));
                if(map!=null){
                    List<String> list = map.get("product_name");
                    if(list!=null) {
                        product_index.setName(list.get(0));
                        System.out.println("高亮后:" + list.get(0));
                    }
                }
            }
            products.add(product_index);
        }

        return products;
    }
}
