package hac.ex4.repo;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * class that save orders made
 * the order have id total price and timestamp when order create and who make the order
 */
@Entity
public class OrderConfirm implements Serializable {

    /**
     * generate id to order
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * save order total price
     */
    @Positive(message="must be greater than 0")
    private double orderSum;

    /**
     * save the order username
     */
    private String orderUserName;

    /**
     * timestamp when order set
     */
    @CreationTimestamp
    private Timestamp orderDate;

    /**
     * default ctor
     */
    public OrderConfirm() {}

    /**
     * ctor
     * @param orderSum total price
     * @param orderUserName when order create
     */
    //ctor - create new order
    public OrderConfirm(double orderSum ,String orderUserName) {
        this.orderSum = orderSum;
        this.orderDate = new Timestamp(System.currentTimeMillis());//set current time
        this.orderUserName = orderUserName;
    }

    /**
     * get function for id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * get function for total price
     * @return orderSum total price
     */
    public double getOrderSum() {
        return orderSum;
    }

    /**
     * get function for username
     * @return orderUserName user make the order
     */
    public String getOrderUserName() {
        return orderUserName;
    }

    /**
     * get function for time of order
     * @return orderDate time of order
     */
    public Timestamp getOrderDate() {return orderDate;}

    /**
     * set function for id
     * @param id - id of the order.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * set function for order sum
     * @param orderSum - sum of the order.
     */
    public void setOrderSum(double orderSum) {
        this.orderSum = orderSum;
    }

    /**
     * set function for order Date
     * @param orderDate - date of the order
     */
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * set function for order UserName
     * @param orderUserName - name of the that create the order.
     */
    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }

    /**
     * convert object to string
     * @return the order object as string
     */
    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", orderSum=" + orderSum + ", orderDate=" + orderDate + '}';
    }
}

