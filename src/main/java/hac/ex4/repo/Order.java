package hac.ex4.repo;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @PositiveOrZero(message="must be equal or greater than 0 , and double")
    private double orderSum;

    @CreationTimestamp
    private Timestamp orderDate;

    public Order() {}

    public Order(double orderSum,Timestamp orderDate) {
        this.orderSum = orderSum;
        this.orderDate = orderDate;

    }

    //get item
    public long getId() {
        return id;
    }
    public double getOrderSum() {
        return orderSum;
    }
    public Timestamp getOrderDate() {return orderDate;}

    //set item
    public void setId(long id) {
        this.id = id;
    }
    public void setOrderSum(double orderSum) {
        this.orderSum = orderSum;
    }
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", orderSum=" + orderSum + ", orderDate=" + orderDate + '}';
    }
}

