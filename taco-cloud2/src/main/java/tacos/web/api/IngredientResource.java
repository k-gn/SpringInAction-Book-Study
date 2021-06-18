package tacos.web.api;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import tacos.Ingredient;
import tacos.Ingredient.Type;

@Getter
public class IngredientResource extends RepresentationModel {

	private String name;

	private Type type;

	public IngredientResource(Ingredient ingredient) {
		this.name = ingredient.getName();
		this.type = ingredient.getType();
	}

}