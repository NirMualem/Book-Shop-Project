package hac.ex4.repo;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class OrderConfirm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Positive(message="must be greater than 0")
    private double orderSum;

    private String orderUserName;

    @CreationTimestamp
    private Timestamp orderDate;

    public OrderConfirm() {}

    public OrderConfirm(double orderSum, Timestamp orderDate , String orderUserName) {
        this.orderSum = orderSum;
        this.orderDate = orderDate;
        this.orderUserName = orderUserName;
    }

    public OrderConfirm(double orderSum ,String orderUserName) {
        this.orderSum = orderSum;
        this.orderDate = new Timestamp(System.currentTimeMillis());
        this.orderUserName = orderUserName;
    }
    //get item
    public long getId() {
        return id;
    }
    public double getOrderSum() {
        return orderSum;
    }
    public String getOrderUserName() {
        return orderUserName;
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
    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", orderSum=" + orderSum + ", orderDate=" + orderDate + '}';
    }
}

