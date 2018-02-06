package solr.po;

/**
 * Created by cooooly on 2018/2/4.
 */
public class Product {
    private Integer pid;
    private String name;
    private Integer catalog;
    private String catalog_name;
    private Double price;
    private Integer number;
    private String description;
    private String picture;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", catalog=" + catalog +
                ", catalog_name='" + catalog_name + '\'' +
                ", price=" + price +
                ", number=" + number +
                ", description='" + description + '\'' +
                '}';
    }

    public Product(Integer pid, String name, Integer catalog, String catalog_name, Double price, Integer number, String description) {
        this.pid = pid;
        this.name = name;
        this.catalog = catalog;
        this.catalog_name = catalog_name;
        this.price = price;
        this.number = number;
        this.description = description;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCatalog() {
        return catalog;
    }

    public void setCatalog(Integer catalog) {
        this.catalog = catalog;
    }

    public String getCatalog_name() {
        return catalog_name;
    }

    public void setCatalog_name(String catalog_name) {
        this.catalog_name = catalog_name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


}
