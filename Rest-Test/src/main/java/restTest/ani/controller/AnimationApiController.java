package restTest.ani.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import restTest.ani.entity.Animation;
import restTest.ani.repository.AnimationRepository;

@RestController
@RequestMapping("/ani")
@RequiredArgsConstructor
public class AnimationApiController {
	
	private final AnimationRepository aniRepo;
	
	@GetMapping
	public CollectionModel<EntityModel<Animation>> allAni() {
		List<Animation> aniList = aniRepo.findAll();
		List<EntityModel<Animation>> modelList = aniList.stream().map(ani -> EntityModel.of(ani, 
				WebMvcLinkBuilder.linkTo(AnimationApiController.class).slash(ani.getId()).withRel("getAni"), 
				WebMvcLinkBuilder.linkTo(AnimationApiController.class).slash(ani.getId()).withRel("updateAni"), 
				WebMvcLinkBuilder.linkTo(AnimationApiController.class).slash(ani.getId()).withRel("deleteAni"))).collect(Collectors.toList());
		
		CollectionModel<EntityModel<Animation>>  hateoasList 
			= CollectionModel.of(modelList, WebMvcLinkBuilder.linkTo(AnimationApiController.class).withSelfRel());
		return hateoasList;
	}
	
	@GetMapping("/{id}")
	public EntityModel<Animation> getAni(@PathVariable Long id) {
		System.out.println("getAni : " + id);
		Optional<Animation> result = aniRepo.findById(id);
		if(result.isPresent()) {
			Animation ani = result.get();
			System.out.println("ani : " + ani);
			EntityModel<Animation> aniModel = EntityModel.of(ani, 
					WebMvcLinkBuilder.linkTo(AnimationApiController.class).slash(id).withSelfRel(),
					WebMvcLinkBuilder.linkTo(AnimationApiController.class).slash(id).withRel("updateAni"),
					WebMvcLinkBuilder.linkTo(AnimationApiController.class).slash(id).withRel("deleteAni"));
			return aniModel;
		}
		return null;
	}
	
	@PostMapping("/")
	public String insertAni(@RequestBody Animation ani) {
		aniRepo.save(ani);
		return null;
	}
	
	@PutMapping("/{id}")
	public String updateAni(@RequestBody Animation ani, @PathVariable Long id) {
		aniRepo.save(ani);
		return null;
	}
	
	@DeleteMapping("/{id}")
	public String deleteAni(@PathVariable Long id) {
		aniRepo.deleteById(id);
		return null;
	}
}
