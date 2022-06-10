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
        return productsCart;
    }

    public void setCart(ArrayList<Product>  products) {
        this.productsCart = products;
    }

    public void add (Product product) {
        productsCart.add(new Product(product.getName() , product.getImage(), product.getQuantity(),  product.getPrice(),  product.getDiscount()));
        productsCart.add(product);
    }

    /* BEAN using ctor - session scope */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Cart sessionBean () {
        Cart cart = new Cart();
        return cart;
    }
}