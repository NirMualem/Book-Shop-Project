package hac.ex4.controllers;

import hac.ex4.repo.OrderConfirm;
import hac.ex4.repo.Product;
import hac.ex4.repo.ProductUser;
import hac.ex4.services.OrderService;
import hac.ex4.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 *admin controller.
 */

@Controller
public class AdminController {

    //product service.
    @Autowired
    private ProductService productService;

    //order service.
    @Autowired
    private OrderService orderService;

    /**
     * get the page of home page of admin
     * @param model -  for update attribute.
     * @return html page of admin index
     */
    @GetMapping("/admin")
    public String main( Model model) {
        model.addAttribute("products", productService.getProducts());
        return "admin/index";
    }

    /**
     * get sign up form.
     * @return html page of add product.
     */
    @GetMapping("/admin/add")
    public String showSignUpForm() {
        return "admin/add-product";
    }

    /**
     * get request to see orders to admin.
     * @param model -  for update attribute.
     * @return html page of admin orders.
     */
    @GetMapping("/admin/orders")
    public String showOrders(Model model) {
        model.addAttribute("orders", orderService.findAllOrderByDate());
        totalOrderSum(model);

        return "admin/orders";
    }

    /**
     * this function calculate the order sun and update the attribute.
     * @param model - the page to add attribute.
     */
    public void totalOrderSum(Model model)
    {
        double sum = 0 ;

        for (OrderConfirm order : orderService.getOrders())
        {
            sum += order.getOrderSum();
        }

        model.addAttribute("totalOrdersSum", sum);
    }

    /**
     * post request to update the DB and add product.
     * @param product - the product to add.
     * @param result - the result of the page.
     * @param model - the html page.
     * @return if success return the home page of admin.
     */
    @PostMapping("/admin/addproduct")
    public String addProduct(@Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/add-product";
        }
        if(product.getImage().equals(""))
        {
            product.setImage("https://nnpbeta.wustl.edu/img/bookCovers/genericBookCover.jpg");
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

    /**
     * post request to get page of edit product
     * @param id - id of the product to update
     * @param model - html page to add attribute.
     * @return html page of update product.
     */
    @PostMapping("/admin/edit")
    public String editProduct(@RequestParam("id") long id, Model model) {
        Product product = productService.getProduct(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

        // the name "Product"  is bound to the VIEW
        model.addAttribute("product", product);
        return "admin/update-product";
    }

    /**
     * post request of update product.
     * @param id - id of the product to update
     * @param product the product to update
     * @param result  - the result
     * @param model - for set attribute.
     * @return html page of update product
     */
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

    /**
     * get request to delete product
     * @param id - id of the product to delete.
     * @param model- for update attribute.
     * @return html page of the shop.
     */
    @GetMapping("/admin/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id, Model model) {

        Product product = productService.getProduct(id).orElseThrow(
                        () -> new IllegalArgumentException("Invalid Product Id:" + id));
        productService.deleteProduct(product);
        model.addAttribute("products", productService.getProducts());
        return "admin/index";
    }


    /**
     * a sample controller return the list of the product of the DB in JSON format
     * @return the list of the product.
     */
    @GetMapping(value="/admin/getjson")
    public @ResponseBody List<Product> getAll() {

        return productService.getProducts();
    }
}

