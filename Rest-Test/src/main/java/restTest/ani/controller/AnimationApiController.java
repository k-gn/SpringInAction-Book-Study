package restTest.ani.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public List<Animation> aniAll() {
		
		return null;
	}
	
	@GetMapping("/{id}")
	public Animation getAni() {
		
		return null;
	}
	
	@PostMapping("/")
	public String insertAni() {
		
		return null;
	}
	
	@PutMapping("/{id}")
	public String updateAni() {
		
		return null;
	}
	
	@DeleteMapping("/{id}")
	public String deleteAni() {
		
		return null;
	}
}
