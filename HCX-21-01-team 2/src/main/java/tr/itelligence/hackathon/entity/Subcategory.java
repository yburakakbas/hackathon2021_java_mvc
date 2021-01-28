package tr.itelligence.hackathon.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Subcategories")
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Category_Id")
    private Category category;

    @OneToMany(mappedBy = "subcategory")
    private List<Product> products;

    public Subcategory() {
    }

    public Subcategory(String description, Category category) {
        this.description = description;
        this.category = category;
    }

    public Subcategory(Long id, String description) {
        this.id = id;
        this.description = description;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Subcategory{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", products=" + products +
                '}';
    }
}
