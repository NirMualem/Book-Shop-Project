package hac.ex4.services;

import hac.ex4.repo.Product;
import hac.ex4.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * service for get product from DB
 */

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    /**
     * save product in DB
     * @param product to save in DB
     */
    @Transactional
    public void addProduct(Product product) {
        repository.save(product);
    }

    /**
     * save product in DB
     * @param product to save in DB
     */
    public void saveProduct(Product product) {
        repository.save(product);
    }

    /**
     * delete product by prod from DB
     * @param prod of product to delete from DB
     */
    public void deleteProduct(Product prod) {
        repository.delete(prod);
    }

    /**
     * get product by id from DB
     * @param id of product to get from DB
     */
    public Optional<Product> getProduct(long id) {
        return repository.findById(id);
    }

    /**
     * get all product from DB
     * @return  all product to get from DB
     */
    public List<Product> getProducts() {
        return repository.findAll();
    }

    /**
     * get 5 top discount product from DB
     * @return 5 top discount product from DB
     */
    public List<Product> getTopDiscountProducts() {
        return repository.findFirst5ByOrderByDiscountDesc();
    }

    /**
     * get product by sub-title from DB
     * @return product by sub-title from DB
     */
    public List<Product> searchByTitle(String title) {
        return repository.findByNameContaining(title);
    }
}
