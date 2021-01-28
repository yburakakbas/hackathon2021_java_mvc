package tr.itelligence.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tr.itelligence.hackathon.dao.*;
import tr.itelligence.hackathon.entity.ProductDetails;
import tr.itelligence.hackathon.entity.Subcategory;
import tr.itelligence.hackathon.entity.*;
import tr.itelligence.hackathon.model.OrderDetailInfo;
import tr.itelligence.hackathon.model.OrderInfo;
import tr.itelligence.hackathon.model.PaginationResult;
import tr.itelligence.hackathon.model.ProductInfo;
import tr.itelligence.hackathon.model.*;
import tr.itelligence.hackathon.util.Utils;
import tr.itelligence.hackathon.validator.ProductInfoValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
// Enable Hibernate Transaction.
@Transactional
// Need to use RedirectAttributes
@EnableWebMvc
public class AdminController {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private SubcategoryDAO subcategoryDAO;

    @Autowired
    private ProductDetailsDAO productDetailsDAO;

    @Autowired
    private ProductInfoValidator productInfoValidator;

    // Configurated In ApplicationContextConfig.
    @Autowired
    private ResourceBundleMessageSource messageSource;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == ProductInfo.class) {
            dataBinder.setValidator(productInfoValidator);
            // For upload Image.
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }

    // GET: Show Login Page
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(Model model) {

        return "login";
    }

    @RequestMapping(value = {"/accountInfo"}, method = RequestMethod.GET)
    public String accountInfo(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getPassword());
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.isEnabled());

        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }

    @RequestMapping(value = {"/orderList"}, method = RequestMethod.GET)
    public String orderList(Model model, //
                            @RequestParam(value = "page", defaultValue = "1") String pageStr) {
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (Exception e) {
        }
        final int MAX_RESULT = 5;
        final int MAX_NAVIGATION_PAGE = 10;

        PaginationResult<OrderInfo> paginationResult //
                = orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);

        model.addAttribute("paginationResult", paginationResult);
        return "orderList";
    }

    // GET: Show product.
    @RequestMapping(value = {"/product"}, method = RequestMethod.GET)
    public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
        ProductInfo productInfo = null;

        if (code != null && code.length() > 0) {
            productInfo = productDAO.findProductInfo(code);
        }
        if (productInfo == null) {
            productInfo = new ProductInfo();
            productInfo.setNewProduct(true);
        }

        List<Category> categoryList = categoryDAO.findAll();
        List<Subcategory> subcategoryList = subcategoryDAO.findAll();

        model.addAttribute("productForm", productInfo);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("subcategoryList", subcategoryList);
        return "product";
    }

    @RequestMapping({"/productDelete"})
    public String removeProduct(HttpServletRequest request, Model model, //
                                @RequestParam(value = "code", defaultValue = "") String code,
                                @RequestParam(value = "name", defaultValue = "") String likeName,
                                @RequestParam(value = "page", defaultValue = "1") int page) {

        List<OrderDetailInfo> orderDetailInfoList = orderDAO.getAllByProductId(code);
        if (orderDetailInfoList != null && orderDetailInfoList.size() > 0) {
            model.addAttribute("productValidation", false);
        } else {
            productDAO.delete(productDAO.findProduct(code));
            model.addAttribute("productValidation", true);
        }

        final int maxResult = 5;
        final int maxNavigationPage = 10;

        PaginationResult<ProductInfo> result = productDAO.queryProducts(page, //
                maxResult, maxNavigationPage, likeName);

        model.addAttribute("paginationProducts", result);

        // Redirect to product list page.
        return "productList";
    }

    // POST: Save product
    @RequestMapping(value = {"/product"}, method = RequestMethod.POST)
    // Avoid UnexpectedRollbackException (See more explanations)
    @Transactional(propagation = Propagation.NEVER)
    public String productSave(Model model, //
                              @ModelAttribute("productForm") @Validated ProductInfo productInfo, //
                              BindingResult result, //
                              final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "product";
        }
        try {
            productDAO.save(productInfo);
        } catch (Exception e) {
            // Need: Propagation.NEVER?
            String message = e.getMessage();
            model.addAttribute("message", message);
            // Show product form.
            return "product";
        }
        return "redirect:/productList";
    }

    @RequestMapping(value = {"/order"}, method = RequestMethod.GET)
    public String orderView(Model model, @RequestParam("orderId") String orderId) {
        OrderInfo orderInfo = null;
        if (orderId != null) {
            orderInfo = this.orderDAO.getOrderInfo(orderId);
        }
        if (orderInfo == null) {
            return "redirect:/orderList";
        }
        List<OrderDetailInfo> details = this.orderDAO.listOrderDetailInfos(orderId);
        orderInfo.setDetails(details);

        model.addAttribute("orderInfo", orderInfo);

        return "order";
    }

    @RequestMapping(value = {"/category"}, method = RequestMethod.GET)
    public String category(Model model) {
        model.addAttribute("category", new Category());
        return "category";
    }

    @RequestMapping(value = {"/category"}, method = RequestMethod.POST)
    // Avoid UnexpectedRollbackException (See more explanations)
    @Transactional(propagation = Propagation.NEVER)
    public String categorySave(Model model, //
                               @ModelAttribute("category") Category category,
                               BindingResult result, //
                               final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "category";
        }
        try {
            categoryDAO.save(category);
        } catch (Exception e) {
            // Need: Propagation.NEVER?
            String message = e.getMessage();
            model.addAttribute("message", message);
            // Show category form
            return "category";
        }
        return "redirect:/productList";
    }

    @RequestMapping(value = {"/subcategory"}, method = RequestMethod.GET)
    public String subcategory(Model model) {
        model.addAttribute("subcategory", new Subcategory());
        return "subcategory";
    }

    @RequestMapping(value = {"/subcategory"}, method = RequestMethod.POST)
    // Avoid UnexpectedRollbackException (See more explanations)
    @Transactional(propagation = Propagation.NEVER)
    public String subcategorySave(Model model, //
                                  @ModelAttribute("subcategory") Subcategory subcategory,
                                  BindingResult result, //
                                  final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "subcategory";
        }
        try {
            subcategoryDAO.save(subcategory);
        } catch (Exception e) {
            // Need: Propagation.NEVER?
            String message = e.getMessage();
            model.addAttribute("message", message);
            // Show category form
            return "subcategory";
        }
        return "redirect:/productList";
    }

    @RequestMapping(value = {"/productDetails"}, method = RequestMethod.GET)
    public String productDetails(Model model, @RequestParam(value = "id", defaultValue = "") Long id) {

        if (id != null && id >= 1) {
            ProductDetails productDetails = productDetailsDAO.findProductDetails(id);
            model.addAttribute("productDetails", productDetails);
        }

        return "productDetails";
    }

    @RequestMapping(value = {"/productDetails"}, method = RequestMethod.POST)
    // Avoid UnexpectedRollbackException (See more explanations)
    @Transactional(propagation = Propagation.NEVER)
    public String categorySave(Model model, //
                               @ModelAttribute("productDetails") ProductDetails productDetails,
                               BindingResult result, //
                               final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "productDetails";
        }
        try {
            productDetailsDAO.save(productDetails);
        } catch (Exception e) {
            // Need: Propagation.NEVER?
            String message = e.getMessage();
            model.addAttribute("message", message);
            // Show category form
            return "productDetails";
        }
        return "redirect:/productList";
    }
}