package tacos.web.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import tacos.Taco;

// Taco -> TacoResource 로 변환하는데 도움을 주는 어셈블러 클래스
public class TacoModelAssembler extends RepresentationModelAssemblerSupport<Taco, TacoModel> {

	public TacoModelAssembler() {
		super(DesignTacoRestController.class, TacoModel.class);
	}
	
	@Override
	protected TacoModel instantiateModel(Taco taco) {
		return new TacoModel(taco);
	}
	
	@Override
	public TacoModel toModel(Taco taco) { // 내부적으로 instantiateModel 호출
		return createModelWithId(taco.getId(), taco);
	}

}
