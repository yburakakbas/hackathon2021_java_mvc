package tr.itelligence.hackathon.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;
import tr.itelligence.hackathon.entity.Category;
import tr.itelligence.hackathon.entity.Product;
import tr.itelligence.hackathon.entity.ProductDetails;
import tr.itelligence.hackathon.entity.Subcategory;

public class ProductInfo {
    private String code;
    private String name;
    private double price;

    private boolean newProduct = false;

    // Upload file.
    private CommonsMultipartFile fileData;

    Category category;

    Subcategory subcategory;

    ProductDetails productDetails;

    public ProductInfo() {
    }

    public ProductInfo(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.subcategory = product.getSubcategory();
        this.productDetails = product.getProductDetails();
    }

    // Không thay đổi Constructor này,
    // nó được sử dụng trong Hibernate query.
    public ProductInfo(String code, String name, double price, Category category, Subcategory subcategory, ProductDetails productDetails) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.category = category;
        this.subcategory = subcategory;
        this.productDetails = productDetails;
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

    public CommonsMultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(CommonsMultipartFile fileData) {
        this.fileData = fileData;
    }

    public boolean isNewProduct() {
        return newProduct;
    }

    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
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
}