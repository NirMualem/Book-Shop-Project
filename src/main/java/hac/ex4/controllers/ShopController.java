package hac.ex4.controllers;

import hac.ex4.beans.Cart;
import hac.ex4.listeners.SessionListenerCounter;
import hac.ex4.repo.OrderConfirm;
import hac.ex4.repo.Product;
import hac.ex4.repo.ProductUser;
import hac.ex4.services.OrderService;
import hac.ex4.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * shop controller.
 */

@Controller
public class ShopController {

    //product service
    @Autowired
    private ProductService productService;

    //order service.
    @Autowired
    private OrderService orderService;

    //for get the session counter.
    @Resource(name="sessionListenerWithMetrics")
    private ServletListenerRegistrationBean<SessionListenerCounter> metrics;

    //create session cart.
    @Resource(name = "sessionBean")
    private Cart sessionCart;

    /**
     * get request to get the shop
     * @param model - for update attribute.
     * @return the html page of the shop
     */
    @GetMapping("/")
    public String main(Model model) {
        // the name "Products"  is bound to the VIEW
        String errors = "" ;
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("topProducts", productService.getTopDiscountProducts());
        addAttributesPayment(model , errors);
        return "user/index";
    }

    /**
     * post request for search book.
     * @param title -  title or a part of the title.
     * @param model- for update attributes.
     * @return the main page with the book that include the title.
     */
    @PostMapping("/user/search")
    public String searchBooks(@RequestParam("title") String title,Model model) {

        model.addAttribute("products",  productService.searchByTitle(title.trim()));
        model.addAttribute("topProducts", productService.getTopDiscountProducts());
        addAttributesPayment(model, "");
        return "user/index";
    }

    /**
     * post request for add book to cart.
     * @param id - id of the book to add
     * @param model - for update attributes
     * @return the main page of the shop.
     */
    @PostMapping("/addProduct")
    public String addProduct(@RequestParam("id") long id, Model model) {
        Product prod = productService.getProduct(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        ProductUser product = new ProductUser(prod);
        sessionCart.add(product);
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("topProducts", productService.getTopDiscountProducts());
        addAttributesPayment(model, "");
        return "user/index";
    }

    /**
     * this function calculate all payment and update the attributes
     * @param model - for update the attribute.
     * @param errors - error to add the page if existed.
     */
    public void addAttributesPayment(Model model , String errors)
    {
        double sum = 0 ;
        double sumWithoutDiscount = 0 ;
        int sumOfCart = 0 ;

        for (ProductUser prod : sessionCart.getCart())
        {
            sum += ((prod.getPrice() - prod.getPrice() * prod.getDiscount()/100) * prod.getCount());
            sumWithoutDiscount += (prod.getPrice() * prod.getCount());
            sumOfCart += prod.getCount();

        }

        model.addAttribute("orderError", errors);
        model.addAttribute("totalDiscount", (sumWithoutDiscount - sum));
        model.addAttribute("totalPrice", sum);
        model.addAttribute("totalProducts", sumOfCart);
        model.addAttribute("sessionCart", sessionCart.getCart());
    }

    /**
     * post request get the payment cart.
     * @param model - for update attribute.
     * @return html page payment.
     */
    @GetMapping("/payment")
    public String process(Model model) {
        String errors = "" ;
        addAttributesPayment(model , errors);
        return "/user/payment";
    }

    /**
     * get request for login to user before payment.
     * @param request Http servlet request.
     * @param model - for update attribute.
     * @return confirm order request.
     */
    @GetMapping("/loginForPay")
    public String loginForPay(HttpServletRequest request, Model model) {

        return "redirect:/confirmOrder";
    }

    /**
     * check the order of the payment
     * if there is error return to the last page else of to the success payment page.
     * @param request -Http servlet request.
     * @param model - for update attribute.
     * @return html page.
     */
    @GetMapping("/confirmOrder")
    public String confirmOrder(HttpServletRequest request, Model model) {
        boolean canComplete = true ;
        String errors = "";
        double sum = 0;
        ArrayList<ProductUser> cart = sessionCart.getCart() ;
        int index = 0 ;
        for (index = 0 ; index < cart.size() ; index++)
        {
            Product product = productService.getProduct(cart.get(index).getId()).orElseThrow(() -> new IllegalArgumentException("Invalid product Id"));
            if(cart.get(index).getCount() > product.getQuantity()) {
                canComplete = false;
                errors += "product \"" + product.getName() + "\" out of stock, the current max of this product is " + product.getQuantity() + ". " ;
            }
            product.setQuantity(product.getQuantity() - cart.get(index).getCount());
            sum += ((cart.get(index).getPrice() - cart.get(index).getPrice() * cart.get(index).getDiscount()/100) * cart.get(index).getCount());
        }
        if(canComplete)
        {
            try{
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                System.out.println(((UserDetails) principal).getUsername());
                OrderConfirm order = new OrderConfirm(sum ,((UserDetails) principal).getUsername());
                orderService.addOrder(order);
            } catch (Exception e) {
                System.out.println(e);
            }
            return "/user/confirmPayment";
        }
        else
        {
            for (index = 0 ; index < cart.size()  ; index++)
            {
                Product product = productService.getProduct(cart.get(index).getId()).orElseThrow(() -> new IllegalArgumentException("Invalid product Id"));
                product.setQuantity(product.getQuantity() + cart.get(index).getCount());
            }
        }
        if(model.getAttribute("orderError") == null)
            addAttributesPayment(model, "");

        addAttributesPayment(model, errors);
        return "/user/payment";
    }

    /**
     * post request to delete product from cart
     * @param id - id of the product to delete.
     * @param request  -Http servlet request.
     * @param model - for update attribute.
     * @return return payment html page.
     */
    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("id") long id,HttpServletRequest request, Model model) {
        Product product = productService.getProduct(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        sessionCart.delete(product.getId());
        if(sessionCart.getCart().size() == 0)
        {
            request.getSession().invalidate();
        }
        addAttributesPayment(model, "");
        return "redirect:/payment";
    }

    /**
     * for increase count of exist product in cart
     * @param id - id of the product to update the count
     * @param model  - for update attribute.
     * @return html page of the shop
     */
    @PostMapping("/increaseProduct")
    public String increaseProduct(@RequestParam("id") long id , Model model) {
        sessionCart.increase(id);
        addAttributesPayment(model, "");
        return "redirect:/payment";
    }

    /**
     * for decrease count of exist product in cart
     * @param id - id of the product to update the count
     * @param model  - for update attribute.
     * @return html page of the shop.
     */
    @PostMapping("/decreaseProduct")
    public String decreaseProduct(@RequestParam("id") long id, Model model) {
        sessionCart.decrease(id);
        addAttributesPayment(model, "");
        return "redirect:/payment";
    }

    /**
     * this get request delete the session.
     * @param request - http servlet request
     * @param model - for update attribute.
     * @return -Http servlet request.
     */
    @GetMapping("/destroySession")
    public String destroySession(HttpServletRequest request ,Model model) {
        request.getSession().invalidate();
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("topProducts", productService.getTopDiscountProducts());
        addAttributesPayment(model, "");
        return "redirect:/";
    }
}