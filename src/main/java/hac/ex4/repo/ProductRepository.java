package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* default scope of this Bean is "singleton" */
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByName(String name);
    /* add here the queries you may need - in addition to CRUD operations
    List<User> findUserByUserName(String userName);
    List<User> findByEmail(String email);
    List<User> findByUserNameAndEmail(String userName, String email);
    List<User> findFirst10ByOrderByUserNameDesc(); */
}
