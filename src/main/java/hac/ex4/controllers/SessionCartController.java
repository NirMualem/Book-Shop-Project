package hac.ex4.controllers;

import hac.ex4.beans.Cart;
import hac.ex4.repo.Product;
import hac.ex4.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SessionCartController {

    @Autowired
    private ProductService productService;

    // injection by ctor: match by name
    @Resource(name = "sessionBean")
    private Cart sessionCart;

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam("id") long id, Model model) {
        Product product = productService.getProduct(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        sessionCart.add(product);
        model.addAttribute("products", productService.getProducts());
        System.out.println("i am  here");
        System.out.println(product);
        return "redirect:/";
    }

    @GetMapping("/payment")
    public String process(Model model) {
        System.out.println(sessionCart.getCart());
        model.addAttribute("sessionCart", sessionCart.getCart());
        return "/user/payment";
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/user/index";
    }
}