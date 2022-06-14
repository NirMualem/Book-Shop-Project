package hac.ex4.beans;

import hac.ex4.repo.Product;
import hac.ex4.repo.ProductUser;
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

    private ArrayList<ProductUser> productsCart;

    public Cart() {
        this.productsCart = new ArrayList<>();
    }

    public ArrayList<ProductUser> getCart() {
        System.out.println(productsCart);
        return productsCart;
    }

    public void setCart(ArrayList<ProductUser> products) {
        this.productsCart = products;
    }

    public void add (ProductUser product) {
        if(productsCart.size()==0)
        {
            productsCart.add(product);
        }
        else {
            for (ProductUser prod : productsCart) {
                if (product.getId() == prod.getId()) {
                    prod.setCount(prod.getCount() + 1);
                    return;
                }
            }
            productsCart.add(product);
        }
    }

    public void delete (long id) {
        productsCart.removeIf(prod -> (prod.getId())==(id));

    }

    public void increase (long id) {
        for (ProductUser prod : productsCart)
            if(prod.getId() == id)
            {
                prod.setCount(prod.getCount()+1);
                return;
            }
    }

    public void decrease (long id) {
        for (ProductUser prod : productsCart)
            if(prod.getId() == id && prod.getCount() > 1)
            {
                prod.setCount(prod.getCount()-1);
                return;
            }
    }

    /* BEAN using ctor - session scope */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Cart sessionBean () {
        Cart cart = new Cart();
        return cart;
    }
}