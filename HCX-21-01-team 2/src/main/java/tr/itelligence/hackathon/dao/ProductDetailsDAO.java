package tr.itelligence.hackathon.dao;

import tr.itelligence.hackathon.entity.Brand;
import tr.itelligence.hackathon.entity.ProductDetails;

import java.util.List;

public interface ProductDetailsDAO {
    void save(ProductDetails productDetails);

    List<ProductDetails> findAll();

    ProductDetails findProductDetails(Long id);
}
