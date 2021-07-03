package tacos.web.api;

import java.util.Date;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import tacos.Taco;

@Getter
// TacoResource 객체 리스트는 tacos로 TacoResource는 taco로 지정
@Relation(value = "taco", collectionRelation = "tacos")
public class TacoModel extends RepresentationModel { // link 객체 리스트와 관리 메소드 상속

//	private static final IngredientResourceAssembler ingredientAssembler = new IngredientResourceAssembler();
	
	private final Long id;
	private final String name;
	private final Date createdAt;
//	private final CollectionModel<IngredientResource> ingredients;
	
	public TacoModel(Taco taco) {
		this.id = taco.getId();
		this.name = taco.getName();
		this.createdAt = taco.getCreatedAt();
//		this.ingredients =  ingredientAssembler.toCollectionModel(taco.getIngredients());
	}
}
