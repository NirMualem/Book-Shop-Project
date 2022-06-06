package hac.ex4.controllers;

import hac.ex4.repo.Product;
import hac.ex4.repo.ProductRepository;
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

//    /* set a default value from the application.properties  file */
//    @Value( "${demo.coursename}" )
//    private String someProperty;

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

    @PostMapping("/addproduct")
    public String addProduct(@Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/add-product";
        }

        productService.saveProduct(product);
        model.addAttribute("products", productService.getProducts());
        return "admin/index";
    }

    /*
     REST style controller

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {

        User user = getRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        // the name "user"  is bound to the VIEW
        model.addAttribute("user", user);
        return "update-user";
    }
    */

    @PostMapping("/edit")
    public String editProduct(@RequestParam("id") long id, Model model) {

        Product product = productService.getProduct(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

        // the name "Product"  is bound to the VIEW
        model.addAttribute("product", product);
        return "admin/update-product";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            product.setId(id);
            return "admin/update-product";
        }
        productService.addProduct(product);
        model.addAttribute("products", productService.getProducts());
        return "admin/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {

        Product product = productService.getProduct(id).orElseThrow(
                        () -> new IllegalArgumentException("Invalid user Id:" + id));
        productService.deleteProduct(product);
        model.addAttribute("products", productService.getProducts());
        return "admin/index";
    }

    @GetMapping(value="/json")
    public String json (Model model) {
        return "json";
    }
    /**
     * a sample controller return the content of the DB in JSON format
     * @return
     */
    @GetMapping(value="/getjson")
    public @ResponseBody List<Product> getAll() {

        return productService.getProducts();
    }
}

