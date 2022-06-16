package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *  default scope of this Bean is "singleton" for get orders
*/
public interface OrderConfirmRepository extends JpaRepository<OrderConfirm, Long> {

    List<OrderConfirm> findAllByOrderByOrderDateDesc();//get all order by desc date order
}
