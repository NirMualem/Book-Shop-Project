package hac.ex4.services;

import hac.ex4.repo.OrderConfirm;
import hac.ex4.repo.OrderConfirmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderConfirmRepository repository;

    @Transactional
    public void addOrder(OrderConfirm order) {
        repository.save(order);
    }
    public List<OrderConfirm> getOrders() {
        return repository.findAll();
    }
    public List<OrderConfirm> findAllOrderByDate() {
        return repository.findAllByOrderByOrderDateDesc();
    }
}
