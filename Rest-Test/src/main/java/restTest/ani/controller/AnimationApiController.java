package restTest.ani.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import restTest.ani.repository.AnimationRepository;

@RestController
@RequestMapping("/ani")
@RequiredArgsConstructor
public class AnimationApiController {
	
	private final AnimationRepository aniRepo;
	
	
}
