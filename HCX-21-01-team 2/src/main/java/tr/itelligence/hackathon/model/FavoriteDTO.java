package tr.itelligence.hackathon.model;

public class FavoriteDTO {

    private long id;

    private String userName;

    private String productCode;

    private double productPrice;

    public FavoriteDTO(long id, String userName, String productCode, double productPrice) {
        this.id = id;
        this.userName = userName;
        this.productCode = productCode;
        this.productPrice = productPrice;
    }

    public FavoriteDTO() {
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
