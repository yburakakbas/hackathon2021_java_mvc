package tr.itelligence.hackathon.dao.impl;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tr.itelligence.hackathon.dao.ProductDAO;
import tr.itelligence.hackathon.entity.*;
import tr.itelligence.hackathon.model.OrderDetailInfo;
import tr.itelligence.hackathon.model.PaginationResult;
import tr.itelligence.hackathon.model.ProductInfo;

import java.util.Date;
import java.util.Locale;

// Transactional for Hibernate
@Transactional
public class ProductDAOImpl implements ProductDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Product findProduct(String code) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Product.class);
        crit.add(Restrictions.eq("code", code));
        return (Product) crit.uniqueResult();
    }

    @Override
    public ProductInfo findProductInfo(String code) {
        Product product = this.findProduct(code);
        if (product == null) {
            return null;
        }
        return new ProductInfo(product.getCode(), product.getName(), product.getPrice(), product.getCategory(), product.getSubcategory(), product.getProductDetails());
    }

    @Override
    public void save(ProductInfo productInfo) {
        String code = productInfo.getCode();

        Product product = null;

        boolean isNew = false;
        if (code != null) {
            product = this.findProduct(code);
        }
        if (product == null) {
            isNew = true;
            product = new Product();
            product.setCreateDate(new Date());
        }
        product.setCode(code);
        product.setName(productInfo.getName());
        product.setPrice(productInfo.getPrice());
        product.setCategory(productInfo.getCategory());
        product.setSubcategory(productInfo.getSubcategory());

        if (productInfo.getFileData() != null) {
            byte[] image = productInfo.getFileData().getBytes();
            if (image != null && image.length > 0) {
                product.setImage(image);
            }
        }
        if (isNew) {
            this.sessionFactory.getCurrentSession().persist(product);
        }
        // If error in DB, Exceptions will be thrown out immediately
        // Nếu có lỗi tại DB, ngoại lệ sẽ ném ra ngay lập tức
        this.sessionFactory.getCurrentSession().flush();
    }

    @Transactional
    @Override
    public void delete(final Product product) {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.delete(product);
        } catch (Exception e) {
            session.close();
            e.printStackTrace();
        }
    }

    @Override
    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage,
                                                       String likeName, String code, Long categoryId, Long subcategoryId) {
        String sql = "Select new " + ProductInfo.class.getName() //
                + "(p.code, p.name, p.price, c, s, pd) " + " from "//
                + Product.class.getName() + " p "
                + " JOIN " + Category.class.getName() + " c "
                + " ON p.category.id = c.id"
                + " JOIN " + Subcategory.class.getName() + " s "
                + " ON p.subcategory.id = s.id"
                + " JOIN " + ProductDetails.class.getName() + " pd"
                + " ON p.productDetails.id = pd.id";

        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(p.name) like :likeName ";
        }

        if (code != null && code.length() > 0) {
            sql += " Where lower(p.code) = :code ";
        }

        if (categoryId != null) {
            sql += " Where p.category.id = :categoryId ";
        }

        if (subcategoryId != null) {
            sql += " Where p.subcategory.id = :subcategoryId ";
        }

        sql += " order by p.createDate desc ";
        //
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery(sql);
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }

        if (code != null && code.length() > 0) {
            query.setParameter("code", code);
        }

        if (categoryId != null) {
            query.setParameter("categoryId", categoryId);
        }

        if (subcategoryId != null) {
            query.setParameter("subcategoryId", subcategoryId);
        }

        return new PaginationResult<ProductInfo>(query, page, maxResult, maxNavigationPage);
    }

    @Override
    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {
        return queryProducts(page, maxResult, maxNavigationPage, null, null, null, null);
    }

    @Override
    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage, String code) {
        return queryProducts(page, maxResult, maxNavigationPage, null, code, null, null);
    }

    @Override
    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage, String likeName, String code) {
        return queryProducts(page, maxResult, maxNavigationPage, likeName, code, null, null);
    }
}