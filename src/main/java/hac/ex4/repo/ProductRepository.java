package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *  default scope of this Bean is "singleton" for get products
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);//find book by name or substring
    List<Product> findFirst5ByOrderByDiscountDesc();//get 5 top discount products
}
