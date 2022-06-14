package hac.ex4.repo;

import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.io.Serializable;

@Component
public class ProductUser extends Product implements Serializable {

    private int count;

    public ProductUser() {}

    public ProductUser(String name, String image, int quantity, double price, double discount) {
        this.setName(name);
        this.setQuantity(quantity);
        this.setPrice(price);
        this.setDiscount(discount);
        this.setImage(image);
        this.count = 1;
    }

    public ProductUser(Product product) {
        this.setName(product.getName());
        this.setQuantity(product.getQuantity());
        this.setPrice(product.getPrice());
        this.setDiscount(product.getDiscount());
        this.setImage(product.getImage());
        this.count = 1;
        this.setId(product.getId());
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}

