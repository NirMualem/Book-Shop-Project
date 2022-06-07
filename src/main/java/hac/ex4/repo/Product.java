package hac.ex4.repo;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Name is mandatory")
    private String name;

    @Value("${https://islandpress.org/sites/default/files/default_book_cover_2015.jpg}")
    private String image;

    @Min(value=0, message="must be equal or greater than 0 , and integer")
    private int quantity;

    @Min(value=0L, message="must be greater than 0")
    private double price;

    @Min(value=0L, message="must be greater than 0")
    @Max(value=100, message="must be lower or equal than 100")
    private double discount ;

    public Product() {}

    public Product(String name, String image, int quantity, double price, double discount) {
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
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

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", image=" + image +
                ", quantity=" + quantity + ", price=" + price + ", discount=" + discount +'}';
    }
}

