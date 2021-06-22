package restTest.ani.controller;

import java.util.List;
import java.util.Optional;

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
import restTest.ani.entity.Company;
import restTest.ani.repository.CompanyRepository;

@RestController
@RequestMapping("/com")
@RequiredArgsConstructor
public class CompanyApiController {
	
	private final CompanyRepository comRepo;
	
	@GetMapping("/")
	public List<Company> allAni() {
		List<Company> comList = comRepo.findAll();
		return comList;
	}
	
	@GetMapping("/{id}")
	public Company getAni(@PathVariable Long id) {
		Optional<Company> result = comRepo.findById(id);
		if(result.isPresent()) {
			Company com = result.get();
			return com;
		}
		return null;
	}
	
	@PostMapping("/")
	public String insertAni(@RequestBody Company ani) {
		comRepo.save(ani);
		return null;
	}
	
	@PutMapping("/{id}")
	public String updateAni(@RequestBody Company ani, @PathVariable Long id) {
		comRepo.save(ani);
		return null;
	}
	
	@DeleteMapping("/{id}")
	public String deleteAni(@PathVariable Long id) {
		comRepo.deleteById(id);
		return null;
	}
}
