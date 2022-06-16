package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *  default scope of this Bean is "singleton" for get products
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * this function get all product that contain the name.
     * @param name - name or part of it.
     * @return all product that contain the name.
     */
    List<Product> findByNameContaining(String name);//find book by name or substring

    /**
     * this function get the top 5 product that order by discount
     * @return top 5 product that order by discount
     */
    List<Product> findFirst5ByOrderByDiscountDesc();//get 5 top discount products
}
