package solr.service;

import org.apache.solr.client.solrj.SolrServerException;
import solr.dao.ProductQuery;
import solr.po.Product;

import java.util.List;

/**
 * Created by cooooly on 2018/2/4.
 */
public interface ProductService {
    //商品搜索
    public List<Product> searchProducts(ProductQuery productQuery) throws SolrServerException;
}
