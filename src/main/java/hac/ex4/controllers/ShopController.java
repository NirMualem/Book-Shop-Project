package hac.ex4.controllers;

import hac.ex4.beans.Cart;
import hac.ex4.repo.Product;
import hac.ex4.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ShopController {

    /* inject via its type the product repo bean - a singleton */
    @Autowired
    private ProductService productService;

    // injection by ctor: match by name
    @Resource(name = "sessionBean")
    private Cart sessionCart;

    @GetMapping("/")
    public String main(Product product, Model model) {
        // the name "Products"  is bound to the VIEW
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("topProducts", productService.getTopDiscountProducts());
        model.addAttribute("sumOfCart", getSumOfCart());
        return "user/index";
    }

    @PostMapping("/user/search")
    public String searchBooks(@RequestParam("title") String title,Model model) {

        model.addAttribute("products",  productService.searchByTitle(title));
        return "user/index";
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam("id") long id, Model model) {
        Product prod = productService.getProduct(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        Product product = new Product(prod);
        sessionCart.add(product);
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("topProducts", productService.getTopDiscountProducts());
        model.addAttribute("sumOfCart", getSumOfCart());
        return "user/index";
    }

    public int getSumOfCart()
    {
        int sum = 0 ;
        for (Product prod : sessionCart.getCart())
        {
            sum += prod.getCount();
        }
        return sum;
    }
    @GetMapping("/payment")
    public String process(Model model) {
        System.out.println(sessionCart.getCart());
        model.addAttribute("sessionCart", sessionCart.getCart());
        return "/user/payment";
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("id") long id, Model model) {
        Product product = productService.getProduct(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        sessionCart.delete(product);
        return "redirect:/payment";
    }

    //for increase count of exist product in cart
    @PostMapping("/increaseProduct")
    public String increaseProduct(@RequestParam("id") long id, Model model) {
        Product product = productService.getProduct(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

        return "redirect:/payment";
    }

    //for decrease count of exist product in cart
    @PostMapping("/decreaseProduct")
    public String decreaseProduct(@RequestParam("id") long id, Model model) {

        return "redirect:/payment";
    }
    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/user/index";
    }

}