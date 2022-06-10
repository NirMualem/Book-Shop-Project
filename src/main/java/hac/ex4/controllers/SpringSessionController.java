package hac.ex4.controllers;

import hac.ex4.beans.Cart;
import hac.ex4.repo.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SpringSessionController {

    // injection by ctor: match by name
    @Resource(name = "sessionBean")
    private Cart sessionCart;

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam("product") Product product) {

        sessionCart.add(product);
        return "redirect:/user/index";
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/user/index";
    }
}