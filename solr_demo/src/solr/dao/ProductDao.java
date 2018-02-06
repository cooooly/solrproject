package solr.dao;

import solr.po.Product;

import java.util.List;

/**
 * Created by cooooly on 2018/2/4.
 */
public interface ProductDao {
    public List<Product> findProducts();
}
