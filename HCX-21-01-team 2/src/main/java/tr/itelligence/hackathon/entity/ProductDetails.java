package tr.itelligence.hackathon.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Product_Details")
public class ProductDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Brand_Id")
    private Brand brand;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Category_Id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "Product_Id")
    private Product product;

    public ProductDetails() {
    }

    public ProductDetails(String description, Brand brand, Category category, Product product) {
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
