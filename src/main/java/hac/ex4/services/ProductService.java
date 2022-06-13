package hac.ex4.services;

import hac.ex4.repo.Product;
import hac.ex4.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional
    public void addProduct(Product product) {
        repository.save(product);
    }

    public void saveProduct(Product product) {
        repository.save(product);
    }
    public void deleteProduct(long id) {
        repository.deleteById(id);
    }
    public void deleteProduct(Product prod) {
        repository.delete(prod);
    }
    public void updateUser(Product prod) {
        repository.save(prod);
    }
    public Optional<Product> getProduct(long id) {
        return repository.findById(id);
    }
    public List<Product> getProducts() {
        return repository.findAll();
    }
    public List<Product> getTopDiscountProducts() {
        return repository.findFirst5ByOrderByDiscountDesc();
    }

    public List<Product> searchByTitle(String title) {
        return repository.findByName(title);
    }
}
