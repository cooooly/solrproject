package solr.dao;

import solr.po.Product;

/**
 * Created by cooooly on 2018/2/4.
 */
public class ProductQuery extends Product {
    //关键字
    private String keywords;
    //商品价格起止
    private Float price_start;
    private Float price_end;
    //排序字段
    private String sortField;

    //排序方式
    private String sortType;
    //当前页码
    private Integer curPage;
    //每页条数
    private Integer rows;
    //总页数
    private Integer pageCount;

    /*总记录数*/
    private Long recordCount;

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Float getPrice_start() {
        return price_start;
    }

    public void setPrice_start(Float price_start) {
        this.price_start = price_start;
    }

    public Float getPrice_end() {
        return price_end;
    }

    public void setPrice_end(Float price_end) {
        this.price_end = price_end;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
}
