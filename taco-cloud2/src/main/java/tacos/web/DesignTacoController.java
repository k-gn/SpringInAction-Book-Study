package tacos.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;
import tacos.Taco;
import tacos.User;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

@Controller
@RequestMapping("/design")
@Slf4j
@RequiredArgsConstructor
// 모델정보 중에서 @SessionAttributes에 지정한 이름에 동일한 것이 있으면 이를 세션에 저장
// @ModelAttribute 애노테이션이 있을 때 전달할 오브젝트를 세션에서 가져오는 것
// 원래는 파라미터에 @ModelAttribute가 있으면 새 오브젝트를 생성한 뒤 HTTP 요청 파라미터를 바인딩한다.
// 하지만 @SessionAttributes를 사용했을 경우는 다르다.
// 새 오브젝트를 생성하기 전 @SessionAttributes에 지정된 이름과 @ModelAttribute 파라미터의 이름을 비교하여 
// 동일할 경우 오브젝트를 새로 생성하지 않고 세션에 있는 오브젝트를 사용
@SessionAttributes("order") // 다수의 타코를 하나의 주문으로 처리하기 위해 세션영역으로 관리
public class DesignTacoController {

	private final IngredientRepository ingredientRepository;
	private final TacoRepository tacoRepository;
	private final UserRepository userRepo;
	
	
	@GetMapping
	public String showDesignForm(Model model, Principal principal) {
		
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepository.findAll().forEach(i -> ingredients.add(i));

		// 각 타입값을 배열로 생성 (WRAP, PROTEIN , ...)
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
//			log.info("=======================");
//			log.info(type.toString());
//			log.info(filterByType(ingredients, type).toString());
			
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}

		String username = principal.getName();
		User user = userRepo.findByUsername(username);
		model.addAttribute("user", user);
//		model.addAttribute("taco", new Taco());

		return "design";
	}

	// 타입별 식재료를 리스트로 반환하는 메소드
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
	}
	
	// 해당 객체를 미리 모델에 생성
	@ModelAttribute(name = "order")
	public Order order() {
		log.info("new order");
		return new Order();
	}
	
	@ModelAttribute(name = "taco")
	public Taco taco() {
		log.info("new taco");
		return new Taco();
	}
	
	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
		// @Valid : 해당 객체는 유효성 검사를 해야 한다고 알림
		// 에러가 있으면 Errors에 매핑된다.
		if(errors.hasErrors()) {
			return "design";
		}
		log.info("processDesign : " + design);
		
		Taco saved = tacoRepository.save(design);
		order.addDesign(saved);
		log.info("order : " + order);
		return "redirect:/orders/current";
	}
}
