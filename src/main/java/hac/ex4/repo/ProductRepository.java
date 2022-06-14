package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* default scope of this Bean is "singleton" */
public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findByNameContaining(String name);
    List<Product> findFirst5ByOrderByDiscountDesc();
}
