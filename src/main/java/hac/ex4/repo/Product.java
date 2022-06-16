package hac.ex4.repo;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * class of product
 * the product have id , name, image, url, quantity, price
 * one product of the shop
 */

@Entity
public class Product implements Serializable {

    /**
     * generate id to product
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * name of book between 2 and 20 chars
     */
    @NotEmpty(message = "Name is mandatory")
    @Size(max=20,min=2, message="name must be 2 and 20 char")
    private String name;

    /**
     * image url
     */
    @URL(message = "url must be valid. keep empty for default image")
    private String image;

    /**
     * quantity in store stock
     */
    @PositiveOrZero(message="must be equal or greater than 0 , and integer")
    private int quantity;

    /**
     * price of product
     */
    @Positive(message="must be greater than 0")
    private double price;

    /**
     * discount on product
     */
    @PositiveOrZero(message="must be between 0 to 99")
    @Max(value=99, message="must be between 0 to 99")
    private double discount ;

    /**
     * default ctor
     */
    public Product() {}

    /**
     * ctor
     * @param name name of product
     * @param image url of product image
     * @param quantity quantity of product
     * @param price price of product
     * @param discount discount on product
     */
    public Product(String name, String image, int quantity, double price, double discount) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.image = image;
    }

    /**
     * copy - ctor
     * @param product product to copy
     */
    public Product(Product product) {
        this.name = product.getName().trim();
        this.quantity = product.getQuantity();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.image = product.getImage().trim();
        this.id = product.getId() ;
    }

    /**
     * get function for id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * get function for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * get function for image url
     * @return image url
     */
    public String getImage() {
        return image;
    }

    /**
     * get function for quantity
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * get function for price
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * get function for discount
     * @return discount
     */
    public double getDiscount() {
        return discount;
    }


    /**
     * set for id
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * set for name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name.trim();
    }

    /**
     * set for image url
     * @param image url the name to set
     */
    public void setImage(String image) {
        this.image = image.trim();
    }

    /**
     * set for quantity
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * set for price
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * set for discount
     * @param discount the discount to set
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * convert object to string
     * @return the product object as string
     */
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", image=" + image +
                ", quantity=" + quantity + ", price=" + price + ", discount=" + discount +'}';
    }
}

