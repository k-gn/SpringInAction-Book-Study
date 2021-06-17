package tacos.data;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tacos.Order;
import tacos.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
