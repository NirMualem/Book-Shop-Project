package hac.ex4.controllers;

import hac.ex4.beans.Cart;
import hac.ex4.repo.Product;
import hac.ex4.repo.ProductUser;
import hac.ex4.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

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
        addAttributesPayment(model);
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
        ProductUser product = new ProductUser(prod);
        sessionCart.add(product);
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("topProducts", productService.getTopDiscountProducts());
        addAttributesPayment(model);
        return "user/index";
    }


    public void addAttributesPayment(Model model)
    {
        int sum = 0 ;
        int sumWithoutDiscount = 0 ;
        int sumOfCart = 0 ;

        for (ProductUser prod : sessionCart.getCart())
        {
            sum += ((prod.getPrice() - prod.getPrice() * prod.getDiscount()/100) * prod.getCount());
            sumWithoutDiscount += (prod.getPrice() * prod.getCount());
            sumOfCart += prod.getCount();

        }
        model.addAttribute("totalDiscount", (sumWithoutDiscount - sum));
        model.addAttribute("totalPrice", sum);
        model.addAttribute("totalProducts", sumOfCart);
        model.addAttribute("sessionCart", sessionCart.getCart());
    }

    @GetMapping("/payment")
    public String process(Model model) {
        addAttributesPayment(model);
        return "/user/payment";
    }

    @GetMapping("/confirmOrder")
    public String confirmOrder(HttpServletRequest request, Model model) {
        boolean canComplete = true ;
        ArrayList<ProductUser> cart = sessionCart.getCart() ;
        int index = 0 ;
        for (index = 0 ; index < cart.size() ; index++)
        {
            Product product = productService.getProduct(cart.get(index).getId()).orElseThrow(() -> new IllegalArgumentException("Invalid product Id"));
            if(cart.get(index).getCount() > product.getQuantity())
            {
                canComplete=false;
                model.addAttribute("orderError", "");
                index--;
                break;
            }
            else
            {
                product.setQuantity(product.getQuantity() - cart.get(index).getCount());
            }
        }
        if(canComplete)
        {
            return "/user/confirmPayment";
        }
        else
        {
            for (; index >= 0  ; index--)
            {
                Product product = productService.getProduct(cart.get(index).getId()).orElseThrow(() -> new IllegalArgumentException("Invalid product Id"));
                product.setQuantity(product.getQuantity() + cart.get(index).getCount());
            }
        }
        addAttributesPayment(model);
        return "/user/payment";
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("id") long id, Model model) {
        Product product = productService.getProduct(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        sessionCart.delete(product.getId());
        addAttributesPayment(model);
        return "redirect:/payment";
    }

    //for increase count of exist product in cart
    @PostMapping("/increaseProduct")
    public String increaseProduct(@RequestParam("id") long id, Model model) {
        sessionCart.increase(id);
        addAttributesPayment(model);
        return "redirect:/payment";
    }

    //for decrease count of exist product in cart
    @PostMapping("/decreaseProduct")
    public String decreaseProduct(@RequestParam("id") long id, Model model) {
        sessionCart.decrease(id);
        addAttributesPayment(model);
        return "redirect:/payment";
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/user/index";
    }



}