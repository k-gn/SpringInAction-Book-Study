package tacos.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import tacos.Order;
import tacos.Taco;

@Repository
public class JdbcOrderRepository implements OrderRepository {

	// SimpleJdbcInsert 
	// query문을 만들 필요가 없다.
	// Primary Key값을 자동으로 생성하고, 생성된 primary key 값을 가지고 올 때도 사용할 수 있다.
	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert orderTacoInserter;
	
	// 형변환을 위해 사용 (보통은 java 객체를 json 데이터로 변환 또는 그 반대일 때 사용)
	private ObjectMapper objectMapper;
	
	@Autowired
	public JdbcOrderRepository(JdbcTemplate jdbc) {
		this.orderInserter = new SimpleJdbcInsert(jdbc)
				.withTableName("Taco_Order")
				.usingGeneratedKeyColumns("id");
		
		this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
				.withTableName("Taco_Order_Tacos");
		
		this.objectMapper = new ObjectMapper();
	}
	
	@Override
	public Order save(Order order) {
		order.setPlacedAt(new Date());
		long orderId = saveOrderDetails(order);
		order.setId(orderId);
		List<Taco> tacos = order.getTacos();
		for (Taco taco : tacos) {
			saveTacoToOrder(taco, orderId);
		}
		
		return order;
	}
	
	private long saveOrderDetails(Order order) {
		@SuppressWarnings("unchecked")
		Map<String, Object> values =
		objectMapper.convertValue(order, Map.class); // order 객체를 map 형태로 변환 (convertValue(변환 전 값, 변환 후 클래스))
		values.put("placedAt", order.getPlacedAt()); // objectMapper 는 날짜 객체를 Long 타입으로 반환하기 때문에 다시 날짜타입으로 setting
		long orderId =
				orderInserter
				.executeAndReturnKey(values) // insert 후 key 값 return
				.longValue();
		return orderId;
	}
	
	private void saveTacoToOrder(Taco taco, long orderId) {
		Map<String, Object> values = new HashMap<>();
		values.put("tacoOrder", orderId);
		values.put("taco", taco.getId());
		orderTacoInserter.execute(values); // insert
	}

}
