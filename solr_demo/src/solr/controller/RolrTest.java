package solr.controller;

import com.sun.xml.internal.fastinfoset.stax.events.StAXEventReader;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by cooooly on 2018/2/4.
 */
public class RolrTest {
    private String  solrUrl = "http://localhost:8080/solr/collection1";


    /*给 solr服务创建索引 , 即添加索引 , 如果id已存在则更新*/
    @Test
    public void createIndex() throws IOException, SolrServerException {
        /*创建solr服务对象*/
        SolrServer server = new HttpSolrServer(solrUrl);

        /*创建document*/
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","8080");
        document.addField("product_keywords","传智");
        document.addField("product_name","java编程");
        document.addField("product_price",55.5f);
        document.addField("product_catalog_name","计算机编程");
        document.addField("product_description","一本值得读的书籍");
        document.addField("product_picture","387383.jpg");
        /*创建索引*/
        UpdateResponse response = server.add(document);
        /*提交*/
        server.commit();
    }

    /*删除索引操作*/
    @Test
    public void deleteIndex() throws IOException, SolrServerException {
        /*创建solr服务对象*/
        SolrServer server = new HttpSolrServer(solrUrl);
        /*根据主键进行删除*/
        //server.deleteById("8080");
        /*自定义条件删除*/
       // server.deleteByQuery("id:8080");
        /*根据id删除全部索引*/
        server.deleteByQuery("id:*");

        /*通过solr可视化界面删除--右侧Documents-选xml-<delete><query>*:*</query><delete><commit/>*/
        server.commit();
    }
}
