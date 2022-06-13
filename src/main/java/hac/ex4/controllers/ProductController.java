package hac.ex4.controllers;

import hac.ex4.repo.Product;
import hac.ex4.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
public class ProductController {

    /* inject via its type the product repo bean - a singleton */
    @Autowired
    private ProductService productService;

    @GetMapping("/admin")
    public String main(Product product, Model model) {
        // the name "Products"  is bound to the VIEW
        model.addAttribute("products", productService.getProducts());
        return "admin/index";
    }

    @GetMapping("/admin/add")
    public String showSignUpForm(Product product, Model model) {
        return "admin/add-product";
    }

    @PostMapping("/admin/addproduct")
    public String addProduct(@Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/add-product";
        }

        try{
            productService.saveProduct(product);
        } catch (Exception e) {
            model.addAttribute("message", "Sorry we could not perform your request!");
        } finally {
            model.addAttribute("products", productService.getProducts());
    }
        return "admin/index";
    }

    @PostMapping("/admin/edit")
    public String editProduct(@RequestParam("id") long id, Model model) {
        Product product = productService.getProduct(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

        // the name "Product"  is bound to the VIEW
        model.addAttribute("product", product);
        return "admin/update-product";
    }

    @PostMapping("/admin/update/{id}")
    public String updateProduct(@PathVariable("id") long id, @Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            product.setId(id);
            return "admin/update-product";
        }
        productService.addProduct(product);
        model.addAttribute("products", productService.getProducts());
        return "admin/index";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id, Model model) {

        Product product = productService.getProduct(id).orElseThrow(
                        () -> new IllegalArgumentException("Invalid Product Id:" + id));
        productService.deleteProduct(product);
        model.addAttribute("products", productService.getProducts());
        return "admin/index";
    }

    @GetMapping(value="/admin/json")
    public String json (Model model) {
        return "json";
    }
    /**
     * a sample controller return the content of the DB in JSON format
     * @return
     */
    @GetMapping(value="/admin/getjson")
    public @ResponseBody List<Product> getAll() {

        return productService.getProducts();
    }
}

