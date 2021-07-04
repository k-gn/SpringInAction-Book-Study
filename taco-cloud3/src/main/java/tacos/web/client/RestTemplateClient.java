package tacos.web.client;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;

@RepositoryRestController
@RequiredArgsConstructor
@Slf4j
public class RestTemplateClient {

	private final RestTemplate rest;
	
	@GetMapping("/get")
	public Ingredient getIngredientById(String ingredientId) {
		Map<String, String> urlVal = new HashMap<>();
		urlVal.put("id", ingredientId);
		
		URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/ingredients/{id}").build(urlVal);
		
		ResponseEntity<Ingredient> responseEntity = rest.getForEntity(uri, Ingredient.class);
		log.info(responseEntity.getHeaders().toString());
		log.info(responseEntity.getBody().toString());
		
		return rest.getForObject(uri, Ingredient.class);
//		return rest.getForObject("http://localhost:8080/ingredients/{id}", Ingredient.class, urlVal);
//		return rest.getForObject("http://localhost:8080/ingredients/{id}", Ingredient.class, ingredientId);
	}
	
	
	@PutMapping("/put")
	public void updateIngredient(Ingredient ingredient) {
		rest.put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.getId());
	}
	
	@DeleteMapping("/delete")
	public void deleteIngredient(Ingredient ingredient) {
		rest.delete("http://localhost:8080/ingredients/{id}", ingredient.getId());
	}
	
	@PostMapping("/post")
	public URI postIngredient(Ingredient ingredient) {
		
//		ResponseEntity<Ingredient> responseEntity = rest.postForEntity("http://localhost:8080/ingredients", ingredient, Ingredient.class);
//		return responseEntity.getBody();
		return rest.postForLocation("http://localhost:8080/ingredients", ingredient); // 반환타입 : 리소스의 URI
//		return rest.postForObject("http://localhost:8080/ingredients", ingredient, Ingredient.class);
	}
	
}
