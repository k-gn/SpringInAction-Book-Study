package tacos.web.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import tacos.Taco;

// Taco -> TacoResource 로 변환하는데 도움을 주는 어셈블러 클래스
public class TacoResourceAssembler extends RepresentationModelAssemblerSupport<Taco, TacoResource> {

	public TacoResourceAssembler() {
		super(DesignTacoController.class, TacoResource.class);
	}
	
	@Override
	protected TacoResource instantiateModel(Taco taco) {
		return new TacoResource(taco);
	}
	
	@Override
	public TacoResource toModel(Taco taco) { // 내부적으로 instantiateModel 호출
		return createModelWithId(taco.getId(), taco);
	}

}
