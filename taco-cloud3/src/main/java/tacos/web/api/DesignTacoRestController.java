package tacos.web.api;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import tacos.Taco;
import tacos.data.TacoRepository;

@RepositoryRestController
@RequiredArgsConstructor
public class DesignTacoRestController {

	private final TacoRepository tacoRepository;
	
	@GetMapping(value =  "/tacos/recent", produces = "application/hal+json")
	public ResponseEntity<CollectionModel<TacoModel>> recentTacos(){
        PageRequest pageRequest = PageRequest.of(0,2, Sort.by("createdAt").descending());
        Iterable<Taco> tacos = tacoRepository.findAll(pageRequest).getContent();
        CollectionModel<TacoModel> collections = new TacoModelAssembler().toCollectionModel(tacos);
        if (tacos.iterator().hasNext()){
            return new ResponseEntity<CollectionModel<TacoModel>>(collections, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
