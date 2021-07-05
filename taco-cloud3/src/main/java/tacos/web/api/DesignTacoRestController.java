package tacos.web.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import tacos.Taco;
import tacos.data.TacoRepository;

@RepositoryRestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DesignTacoRestController {

	private final TacoRepository tacoRepository;
	
	@Value("${spring.data.rest.base-path}")
	private String basePath;
	
	@GetMapping(value =  "/tacos/recent", produces = "application/hal+json")
	public ResponseEntity<CollectionModel<TacoModel>> recentTacos(){
        PageRequest pageRequest = PageRequest.of(0,2, Sort.by("createdAt").descending());
        Iterable<Taco> tacos = tacoRepository.findAll(pageRequest).getContent();
        CollectionModel<TacoModel> collections = new TacoModelAssembler().toCollectionModel(tacos);
        collections.add(linkTo(methodOn(DesignTacoRestController.class).recentTacos()).withSelfRel());
        if (tacos.iterator().hasNext()){
            return new ResponseEntity<CollectionModel<TacoModel>>(collections, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
