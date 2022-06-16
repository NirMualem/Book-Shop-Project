package hac.ex4.repo;

import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * Product for user extends from Product
 * hold one more field count that save the amount specific user want from the product
 */
@Component
public class ProductUser extends Product implements Serializable {

    private int count;//save the amount specific user want from the product

    /**
     * default ctor
     */
    public ProductUser() {}

    /**
     * ctor
     * @param name name of product
     * @param image url of product image
     * @param quantity quantity of product
     * @param price price of product
     * @param discount discount on product
     */
    public ProductUser(String name, String image, int quantity, double price, double discount) {
        this.setName(name);
        this.setQuantity(quantity);
        this.setPrice(price);
        this.setDiscount(discount);
        this.setImage(image);
        this.count = 1; //set default as 1
    }

    /**
     * copy - ctor for userProduct
     * @param product product to copy
     */
    public ProductUser(Product product) {
        this.setName(product.getName());
        this.setQuantity(product.getQuantity());
        this.setPrice(product.getPrice());
        this.setDiscount(product.getDiscount());
        this.setImage(product.getImage());
        this.count = 1;
        this.setId(product.getId());
    }

    /**
     * get function for count
     * @return count
     */
    public int getCount() {
        return count;
    }

    /**
     * set for id
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

}

