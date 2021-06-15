package tacos.web;

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
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

@Controller
@RequestMapping("/design")
@Slf4j
@RequiredArgsConstructor
@SessionAttributes("order") // 다수의 타코를 하나의 주문으로 처리하기 위해 세션영역으로 관리
public class DesignTacoController {

	private final IngredientRepository ingredientRepository;
	private final TacoRepository tacoRepository;
	
	
	
	@GetMapping
	public String showDesignForm(Model model) {
		// 식재료 리스트 생성
//		List<Ingredient> ingredients = Arrays.asList(
//				new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//				new Ingredient("COTO", "Corn Tortilla", Type.WRAP), 
//				new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//				new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//				new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES), 
//				new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//				new Ingredient("CHED", "Cheddar", Type.CHEESE), 
//				new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//				new Ingredient("SLSA", "Salsa", Type.SAUCE),
//				new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
		
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepository.findAll().forEach(i -> ingredients.add(i));

		// 각 타입값을 배열로 생성 (WRAP, PROTEIN , ...)
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			log.info("=======================");
			log.info(type.toString());
			log.info(filterByType(ingredients, type).toString());
			
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}

		model.addAttribute("taco", new Taco());

		return "design";
	}

	// 타입별 식재료를 리스트로 반환하는 메소드
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
	}
	
	// 해당 객체를 미리 모델에 생성
	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}
	
	@ModelAttribute(name = "taco")
	public Taco taco() {
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
		
		return "redirect:/orders/current";
	}
}
