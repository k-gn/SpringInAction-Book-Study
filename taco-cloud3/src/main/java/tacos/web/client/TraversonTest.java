package tacos.web.client;

import java.net.URI;
import java.util.Collection;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import tacos.Ingredient;
import tacos.Taco;

@RestController
@RequestMapping("/use")
@RequiredArgsConstructor
public class TraversonTest {

	// API 에 하이퍼링크를 포함해야 한다면 RestTemplate 는 도움이 안된다.
	// 따라서 Traverson 같은 클라이언트 라이브러리를 사용하는 것이 좋다. (RestTemplate 을 더 상세히 다루면 가능은 하지만 간단하지 않음)
	// Traverson 은 스프링 데이터 HATEOAS 와 같이 제공된다.
	// Traverson 을 사용할 때 먼저 해당 API 의 기본 URI 를 갖는 객체를 생성한다.
	// base url + hal-json 지정(Traverson이 수신되는 리소스 데이터를 분석하는 방법을 알수 있다)
	private final Traverson traverson;
	private final RestTemplate rest;
	
	@GetMapping("/tra")
	public Collection<Taco> traversonTest() {
		
		System.out.println("traversonTest");
		
		ParameterizedTypeReference<CollectionModel<Ingredient>> ingredientType = new ParameterizedTypeReference<CollectionModel<Ingredient>>() {};
		ParameterizedTypeReference<CollectionModel<Taco>> tacoType = new ParameterizedTypeReference<CollectionModel<Taco>>() {};
		
		CollectionModel<Ingredient> ingredientRes = traverson
						.follow("ingredients") // 리소스 링크의 관계 이름이 ingredients 인 리소스로 이동
						.toObject(ingredientType); // 읽어오는 데이터 타입을 지정 (이 때 ParameterizedTypeReference 를 사용해서 지정한다)
		
		Collection<Ingredient> ingredients = ingredientRes.getContent();
		
		CollectionModel<Taco> tacoRes = traverson
//				.follow("tacos", "recent") 
				.follow("tacos") 
//				.follow("recent") 
				.toObject(tacoType); 
		
		System.out.println(rest.getForObject("http://localhost:8080/api/tacos", CollectionModel.class));
		
		Collection<Taco> tacos = tacoRes.getContent();
		return tacos;
	}
	
	@GetMapping("/tralink")
	public String addIngredient(Ingredient ingredient) {
		String ingredientUrl = traverson
				.follow("ingredients")
				.asLink() // 링크 자체를 요청
				.getHref(); // 링크의 url 가져오기
		
		return ingredientUrl;
//		return rest.postForObject(ingredientUrl, ingredient, Ingredient.class);
	}
}
