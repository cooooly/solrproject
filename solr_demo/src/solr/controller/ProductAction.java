package solr.controller;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import solr.dao.ProductQuery;
import solr.po.Product;
import solr.service.ProductService;

import java.util.List;

/**
 * Created by cooooly on 2018/2/5.
 */
@Controller
@RequestMapping("/product")
public class ProductAction {
    @Autowired
    private ProductService productService;

    /*页面请求路径的映射*/
    @RequestMapping("/search")
    public String search(Model model,ProductQuery productQuery) throws SolrServerException {
        /*调用service层,service通过solr服务,获取所有查询*/
        List<Product> products = productService.searchProducts(productQuery);
        /*用model将数据存入request域,页面通过el表达式获取*/
        model.addAttribute("list",products);
        /*返回一个逻辑视图,和springmvc.xml里的视图解析器,一起构成一个物理地址*/
        return "search";
    }


}
