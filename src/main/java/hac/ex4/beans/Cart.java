package hac.ex4.beans;

import hac.ex4.repo.ProductUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represent Cart of user that hold books that the user want.
 * the class hold list of product of user.
 */
@Component
public class Cart implements Serializable {

    //hold array of productUser
    private ArrayList<ProductUser> productsCart;

    //ctor
    public Cart() {
        this.productsCart = new ArrayList<>();
    }

    /**
     * get function for cart
     * @return list of product.
     */
    public ArrayList<ProductUser> getCart() {
        return productsCart;
    }

    /**
     * set function fot cart.
     * @param products list of product for set the array.
     */
    public void setCart(ArrayList<ProductUser> products) {
        this.productsCart = products;
    }

    /**
     * this function add to the array product
     * @param product to add the cart.
     */
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

    /**
     * this function delete by id product in the cart.
     * @param id to get product and delete it.
     */
    public void delete (long id) {
        productsCart.removeIf(prod -> (prod.getId())==(id));

    }

    /**
     * this function get id to increase the count of the book.
     * @param id of the product to increase the count.
     */
    public void increase (long id) {
        for (ProductUser prod : productsCart)
            if(prod.getId() == id)
            {
                prod.setCount(prod.getCount()+1);
                return;
            }
    }

    /**
     * this function get id to decrease the count of the book.
     * @param id of the product to decrease the count.
     */
    public void decrease (long id) {
        for (ProductUser prod : productsCart)
            if(prod.getId() == id && prod.getCount() > 1)
            {
                prod.setCount(prod.getCount()-1);
                return;
            }
    }

    /**
     * BEAN using ctor -session scope
     * @return the cart- list of product.
     */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Cart sessionBean () {
        Cart cart = new Cart();
        return cart;
    }
}