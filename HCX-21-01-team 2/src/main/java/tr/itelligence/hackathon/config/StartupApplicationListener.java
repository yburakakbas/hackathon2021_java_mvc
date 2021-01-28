package tr.itelligence.hackathon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import tr.itelligence.hackathon.dao.*;
import tr.itelligence.hackathon.entity.*;
import tr.itelligence.hackathon.model.ProductInfo;

import java.util.Date;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final AccountDAO accountDAO;
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final SubcategoryDAO subcategoryDAO;
    private final BrandDAO brandDAO;
    private final ProductDetailsDAO productDetailsDAO;

    @Autowired
    public StartupApplicationListener(AccountDAO accountDAO,
                                      ProductDAO productDAO,
                                      CategoryDAO categoryDAO,
                                      SubcategoryDAO subcategoryDAO,
                                      BrandDAO brandDAO,
                                      ProductDetailsDAO productDetailsDAO) {
        this.accountDAO = accountDAO;
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.subcategoryDAO = subcategoryDAO;
        this.brandDAO = brandDAO;
        this.productDetailsDAO = productDetailsDAO;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        try {
            accountDAO.save(new Account("MANAGER", "123", true, Account.ROLE_MANAGER));
            accountDAO.save(new Account("EMPLOYEE", "123", true, Account.ROLE_EMPLOYEE));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Category categoryE = new Category("Electronics");
        Category categoryH = new Category("Home Products");
        Category categoryF = new Category("Food and Beverage");
        Category categoryC = new Category("Cosmetics");

        categoryDAO.save(categoryE);
        categoryDAO.save(categoryH);
        categoryDAO.save(categoryF);
        categoryDAO.save(categoryC);

        Subcategory subcategoryTV = new Subcategory("TV", categoryE);
        Subcategory subcategoryFurn = new Subcategory("Furniture", categoryF);
        Subcategory subcategoryFood = new Subcategory("Food", categoryF);
        Subcategory subcategoryPerfume = new Subcategory("Perfume", categoryC);

        subcategoryDAO.save(subcategoryTV);
        subcategoryDAO.save(subcategoryFurn);
        subcategoryDAO.save(subcategoryFood);
        subcategoryDAO.save(subcategoryPerfume);

        Brand brandS = new Brand("Samsung");
        Brand brandH = new Brand("Yataş");
        Brand brandA = new Brand("Burger House");
        Brand brandB = new Brand("Burberry");

        brandDAO.save(brandS);
        brandDAO.save(brandH);
        brandDAO.save(brandA);
        brandDAO.save(brandB);

        Product productTV = new Product("S1111",
                "TV",
                3450.0,
                null,
                new Date(),
                categoryE,
                subcategoryTV);

        Product productSofa = new Product("S1112",
                "L Sofa",
                2412.5,
                null,
                new Date(),
                categoryH,
                subcategoryFurn);

        Product productBurger = new Product("S1113",
                "BBQ Burger",
                20.99,
                null,
                new Date(),
                categoryF,
                subcategoryFood);

        Product productBurberry = new Product("S1114",
                "Burberry 430",
                15,
                null,
                new Date(),
                categoryC,
                subcategoryPerfume);

        productDAO.save(new ProductInfo(productTV));
        productDAO.save(new ProductInfo(productSofa));
        productDAO.save(new ProductInfo(productBurger));
        productDAO.save(new ProductInfo(productBurberry));

        ProductDetails productDetailsA = new ProductDetails("Büyük TV", brandS, categoryE, productTV);
        ProductDetails productDetailsB = new ProductDetails("Küçük L", brandH, categoryH, productSofa);
        ProductDetails productDetailsC = new ProductDetails("Devasa Burger", brandA, categoryF, productBurger);
        ProductDetails productDetailsD = new ProductDetails("Yüzyılın Parfümü", brandB, categoryC, productBurberry);

        productDetailsDAO.save(productDetailsA);
        productDetailsDAO.save(productDetailsB);
        productDetailsDAO.save(productDetailsC);
        productDetailsDAO.save(productDetailsD);
    }
}
