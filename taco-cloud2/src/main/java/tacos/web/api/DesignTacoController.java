package tacos.web.api;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tacos.Taco;
import tacos.data.TacoRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController("designApiTacoController")
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin(origins = "*") // 서로다른 도메인간의 요청을 허용
@Slf4j
@RequiredArgsConstructor
public class DesignTacoController {

	private final TacoRepository tacoRepo;
	private final EntityLinks entityLinks;

	@GetMapping("/recent") 	
	public CollectionModel<TacoResource> recentTacos() {
//		EntityModel<Taco>
//		CollectionModel<Taco>
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		
		List<Taco> tacos = tacoRepo.findAll(page).getContent();
		CollectionModel<TacoResource> recentResources = new TacoResourceAssembler().toCollectionModel(tacos);
		recentResources.add(WebMvcLinkBuilder.linkTo(DesignTacoController.class).slash("recent").withRel("recents")); // 관련 경로 추가
//		recentResources.add(linkTo(methodOn(DesignTacoController.class).recentTacos()).withRel("recents")); // 관련 경로 추가
		return recentResources;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
		Optional<Taco> optTaco = tacoRepo.findById(id);
		if (optTaco.isPresent()) {
			return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@PostMapping(consumes = "application/json") // application/json 요청만 처리
	@ResponseStatus(HttpStatus.CREATED)
	public Taco postTaco(@RequestBody Taco taco) {
		return tacoRepo.save(taco);
	}
}
