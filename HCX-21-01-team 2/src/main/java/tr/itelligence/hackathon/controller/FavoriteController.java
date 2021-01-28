package tr.itelligence.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import tr.itelligence.hackathon.dao.FavoriteDAO;
import tr.itelligence.hackathon.dao.OrderDAO;
import tr.itelligence.hackathon.dao.ProductDAO;
import tr.itelligence.hackathon.entity.Account;
import tr.itelligence.hackathon.entity.Favorite;
import tr.itelligence.hackathon.entity.Product;
import tr.itelligence.hackathon.model.FavoriteDTO;
import tr.itelligence.hackathon.model.OrderInfo;
import tr.itelligence.hackathon.model.PaginationResult;
import tr.itelligence.hackathon.model.ProductInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
// Enable Hibernate Transaction.
@Transactional
// Need to use RedirectAttributes
@EnableWebMvc
public class FavoriteController {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private FavoriteDAO favoriteDAO;

    @RequestMapping(value = {"/favorite"}, method = RequestMethod.POST)
    // Avoid UnexpectedRollbackException (See more explanations)
    public String favoriteSave(HttpServletRequest request, Model model,
                               @ModelAttribute("favoriteDTO") FavoriteDTO favoriteDTO, @RequestParam(value = "name", defaultValue = "") String likeName,
                               @RequestParam(value = "page", defaultValue = "1") int page) {

        Favorite favorite = favoriteDAO.getByUserNameAndProductCode(favoriteDTO.getUserName(), favoriteDTO.getProductCode());
        if(favorite != null) {
            model.addAttribute("validationFavorite", false);
        } else {
            Account account = new Account();
            account.setUserName(favoriteDTO.getUserName());

            Product product = new Product();
            product.setCode(favoriteDTO.getProductCode());

            Favorite favorite1 = new Favorite();
            favorite1.setAccount(account);
            favorite1.setProduct(product);

            favoriteDAO.save(favorite1);
            model.addAttribute("validationFavorite", true);
        }

        final int maxResult = 5;
        final int maxNavigationPage = 10;

        PaginationResult<ProductInfo> result = productDAO.queryProducts(page, //
                maxResult, maxNavigationPage, likeName);

        model.addAttribute("paginationProducts", result);

        // Redirect to product list page.
        return "productList";
    }

    @RequestMapping(value = {"/favoriteList"}, method = RequestMethod.GET)
    public String favoriteList(Model model, //
                               @RequestParam(value = "page", defaultValue = "1") String pageStr,  @RequestParam(value = "userName", defaultValue = "") String userName) {
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (Exception e) {
        }
        final int MAX_RESULT = 5;
        final int MAX_NAVIGATION_PAGE = 10;

        PaginationResult<FavoriteDTO> favoriteDTOList //
                = favoriteDAO.getAllByUserName(userName, page, MAX_RESULT, MAX_NAVIGATION_PAGE);

        model.addAttribute("paginationResult", favoriteDTOList);
        return "favoriteList";
    }

}
