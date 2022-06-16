package hac.ex4.services;

import hac.ex4.repo.OrderConfirm;
import hac.ex4.repo.OrderConfirmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * service for get orders details from DB
 */
@Service
public class OrderService {

    @Autowired
    private OrderConfirmRepository repository;

    /**
     * save order in DB
     * @param order to save in DB
     */
    @Transactional
    public void addOrder(OrderConfirm order) {
        repository.save(order);
    }

    /**
     * get all orders in DB
     * @return all orders exist in DB
     */
    public List<OrderConfirm> getOrders() {
        return repository.findAll();
    }

    /**
     * get all orders in DB by decs date
     * @return all orders exist in DB by decs date
     */
    public List<OrderConfirm> findAllOrderByDate() {
        return repository.findAllByOrderByOrderDateDesc();
    }
}
