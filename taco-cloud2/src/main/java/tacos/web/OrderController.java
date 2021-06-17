package tacos.web;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tacos.Order;
import tacos.User;
import tacos.data.OrderProps;
import tacos.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order") // 세션 order 사용
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderProps props;
	private final OrderRepository orderRepository;
	
	@GetMapping("/current")
	public String orderForm(@AuthenticationPrincipal User user,
			@ModelAttribute Order order) {
		if (order.getDeliveryName() == null) {
			order.setDeliveryName(user.getFullname());
		}
		if (order.getDeliveryStreet() == null) {
			order.setDeliveryStreet(user.getStreet());
		}
		if (order.getDeliveryCity() == null) {
			order.setDeliveryCity(user.getCity());
		}
		if (order.getDeliveryState() == null) {
			order.setDeliveryState(user.getState());
		}
		if (order.getDeliveryZip() == null) {
			order.setDeliveryZip(user.getZip());
		}
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, 
			SessionStatus sessionStatus, 
			@AuthenticationPrincipal User user) {
		if(errors.hasErrors()) {
			return "orderForm";
		}
		
		log.info("order : " + order);
		order.setUser(user);
		orderRepository.save(order);
		// @SessionAttributes를 사용해 세션에 저장한 모델이 더 이상 필요없어질 경우 세션에서 제거해줘야 한다.
		// 세션의 제거는 SessionStatus 오브젝트의 setComplete 메서드로 제거
		sessionStatus.setComplete();
		
		log.info("Order submit : " + order);
		return "redirect:/";
	}
	
	
	@GetMapping
	public String ordersForUser(
			@AuthenticationPrincipal User user, Model model) {
		
		Pageable pageable = PageRequest.of(0, props.getPageSize());
		model.addAttribute("orders",
				orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
		return "orderList";
	}
}