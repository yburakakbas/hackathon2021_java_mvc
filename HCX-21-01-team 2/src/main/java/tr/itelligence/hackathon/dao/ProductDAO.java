package tr.itelligence.hackathon.dao;

import tr.itelligence.hackathon.entity.Product;
import tr.itelligence.hackathon.model.PaginationResult;
import tr.itelligence.hackathon.model.ProductInfo;

public interface ProductDAO {

    public Product findProduct(String code);

    public ProductInfo findProductInfo(String code);

    public PaginationResult<ProductInfo> queryProducts(int page,
                                                       int maxResult, int maxNavigationPage);

    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult,
                                                       int maxNavigationPage, String likeName);

    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult,
                                                       int maxNavigationPage, String likeName, String code);

    public void save(ProductInfo productInfo);

    PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage,
                                                String likeName, String code, Long categoryId, Long subcategoryId);
    public void delete(final Product product);

}