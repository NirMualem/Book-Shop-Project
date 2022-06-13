package hac.ex4.repo;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Name is mandatory")
    private String name;

    @URL(message = "url must be valid. keep empty for default image")
    private String image;

    @PositiveOrZero(message="must be equal or greater than 0 , and integer")
    private int quantity;

    private int count;

    @Positive(message="must be greater than 0")
    private double price;

    @PositiveOrZero(message="must be between 0 to 100")
    @Max(value=100, message="must be between 0 to 100")
    private double discount ;

    public Product() {}

    public Product(String name, String image, int quantity, double price, double discount) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.image = image;
        this.count = 1;
    }

    public Product(Product product) {
        this.name = product.getName();
        this.quantity = product.getQuantity();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.image = product.getImage();
        this.count = 1;
        this.id = product.getId() ;
    }

    //get item
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }
    public double getDiscount() {
        return discount;
    }
    public int getCount() {
        return count;
    }

    //set item
    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", image=" + image +
                ", quantity=" + quantity + ", price=" + price + ", discount=" + discount +'}';
    }
}

