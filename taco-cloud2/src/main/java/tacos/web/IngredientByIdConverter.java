package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.convert.converter.Converter;

import tacos.Ingredient;
import tacos.data.IngredientRepository;

@Component
@Slf4j
public class IngredientByIdConverter implements Converter<String, Ingredient>{
	// Converter 인터페이스에 지정한 타입 변환이 필요할 때 convert() 자동 호출된다.
	private IngredientRepository ingredientRepo;
	
	@Autowired
	public IngredientByIdConverter(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}
	
	@Override
	public Ingredient convert(String id) {
		log.info("converter : " + id);
		return ingredientRepo.findById(id);
	}
	
}
