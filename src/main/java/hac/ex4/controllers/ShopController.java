package hac.ex4.controllers;

import hac.ex4.repo.Product;
import hac.ex4.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ShopController {

    /* inject via its type the product repo bean - a singleton */
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String main(Product product, Model model) {
        // the name "Products"  is bound to the VIEW
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("topProducts", productService.getTopDiscountProducts());

        return "user/index";
    }

    @PostMapping("/user/search")
    public String searchBooks(@RequestParam("title") String title,Model model) {

        model.addAttribute("products",  productService.searchByTitle(title));
        return "user/index";
    }
}

