package solr.controller;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import solr.dao.ProductDao;
import solr.po.Product;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by cooooly on 2018/2/4.
 */
public class MybatisGetDataTest {
    private SqlSessionFactory sqlSessionFactory;
    @Before
    public void setUp() throws IOException {
        String resource = "SqlMapConfig.xml";
        InputStream is = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    }
    @Test
    public void useMybatisGetDataTest(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ProductDao mapper = sqlSession.getMapper(ProductDao.class);
        List<Product> products = mapper.findProducts();
        System.out.println(products);
        sqlSession.close();
    }
}
