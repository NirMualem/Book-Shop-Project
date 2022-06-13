package hac.ex4.beans;

import hac.ex4.repo.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.util.ArrayList;

/* this is a bean class instantiated in session */

@Component
public class Cart implements Serializable {

    private ArrayList<Product> productsCart;

    public Cart() {
        this.productsCart = new ArrayList<>();
    }

    public ArrayList<Product> getCart() {
        System.out.println(productsCart);
        return productsCart;
    }

    public void setCart(ArrayList<Product>  products) {
        this.productsCart = products;
    }

    public void add (Product product) {
        System.out.println(product.getId());
        if(productsCart.size()==0)
        {
            productsCart.add(product);
        }
        else {
            for (Product prod : productsCart) {
                if (product.getId() == prod.getId()) {
                    prod.setCount(prod.getCount() + 1);
                    return;
                }
            }
            productsCart.add(product);
        }
    }

    public void delete (Product product) {
        productsCart.removeIf(prod -> (prod.getId())==(product.getId()));
        productsCart.remove(product);
        System.out.println(productsCart);
    }

    /* BEAN using ctor - session scope */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Cart sessionBean () {
        Cart cart = new Cart();
        return cart;
    }
}