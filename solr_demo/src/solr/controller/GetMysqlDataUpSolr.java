package solr.controller;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;
import solr.dao.ProductDao;
import solr.po.Product;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by cooooly on 2018/2/4.
 */
/*从数据库获取数据,添加到solr服务为索引*/
public class GetMysqlDataUpSolr {
    private SqlSessionFactory sqlSessionFactory;
    private String solrUrl = "http://localhost:8080/solr/collection1";

    @Before
    public void setUp() throws IOException {
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void mysqlToSolr() throws IOException, SolrServerException {
        /*从数据库获取数据*/
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ProductDao mapper = sqlSession.getMapper(ProductDao.class);
        List<Product> products = mapper.findProducts();
        /*拿到数据遍历之后转化为solr所需字段,创建索引到solr*/

        /*创建solr服务对象*/
        SolrServer server = new HttpSolrServer(solrUrl);

         /*集合存储document对象*/
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();


        for (Product product : products) {
             /*创建document对象*/
            SolrInputDocument document = new SolrInputDocument();
            /*给document加入filed域*/
            document.addField("id", product.getPid());
            document.addField("product_name", product.getName());
            document.addField("product_price", product.getPrice());
            document.addField("product_catalog_name", product.getCatalog_name());
            document.addField("product_description", product.getDescription());
            document.addField("product_picture", product.getPicture());
            documents.add(document);
        }
         /*创建索引*/
        server.add(documents);
        server.commit();
    }

}
