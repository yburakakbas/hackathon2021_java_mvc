package tr.itelligence.hackathon.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Products")
public class Product implements Serializable {

    private static final long serialVersionUID = -1000119078147252957L;

    @Id
    @Column(name = "Code", length = 20, nullable = false)
    private String code;

    @Column(name = "Name", length = 255, nullable = false)
    private String name;

    @Column(name = "Price", nullable = false)
    private double price;

    @Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;

    // For sort.
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Create_Date", nullable = false)
    private Date createDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Category_Id")
    private Category category;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Subcategory_Id")
    private Subcategory subcategory;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductDetails productDetails;

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<Favorite> favoriteList;

    public Product() {
    }

    public Product(String code,
                   String name,
                   double price,
                   byte[] image,
                   Date createDate,
                   Category category,
                   Subcategory subcategory) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.image = image;
        this.createDate = createDate;
        this.category = category;
        this.subcategory = subcategory;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image=" + Arrays.toString(image) +
                ", createDate=" + createDate +
                ", category=" + category +
                ", subcategory=" + subcategory +
                ", productDetails=" + productDetails +
                '}';
    }
}