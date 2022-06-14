package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* default scope of this Bean is "singleton" */
public interface OrderConfirmRepository extends JpaRepository<OrderConfirm, Long> {

    //List<OrderConfirm> findAllOrderByDateDesc();
    /* add here the queries you may need - in addition to CRUD operations
    List<User> findUserByUserName(String userName);
    List<User> findByEmail(String email);
    List<User> findByUserNameAndEmail(String userName, String email);
    List<User> findFirst10ByOrderByUserNameDesc(); */
}
