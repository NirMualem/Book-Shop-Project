package hac.ex4.services;

import hac.ex4.repo.Order;
import hac.ex4.repo.OrderRepository;
import hac.ex4.repo.Product;
import hac.ex4.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional
    public void addOrder(Order order) {
        repository.save(order);
    }
    public List<Order> getOrders() {
        return repository.findAll();
    }
    public List<Order> getAllOrderByDate() {
        return repository.findAll();
    }
}
