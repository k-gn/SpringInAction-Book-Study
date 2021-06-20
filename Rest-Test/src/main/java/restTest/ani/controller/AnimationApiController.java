package restTest.ani.controller;

import java.util.List;

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
	
	@GetMapping("/")
	public List<Animation> allAni() {
		aniRepo.findAll();
		return null;
	}
	
	@GetMapping("/{id}")
	public Animation getAni(@PathVariable Long id) {
		aniRepo.findById(id);
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
