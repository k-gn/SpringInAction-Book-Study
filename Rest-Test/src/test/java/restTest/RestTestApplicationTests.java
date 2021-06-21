package restTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import restTest.ani.repository.AnimationRepository;
import restTest.ani.repository.CompanyRepository;

@SpringBootTest
class RestTestApplicationTests {
	
	@Autowired
	private AnimationRepository aniRepo;
	
	@Autowired
	private CompanyRepository comRepo;

	@Test
	void contextLoads() {
		
	}

}
