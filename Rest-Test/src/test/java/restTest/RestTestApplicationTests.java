package restTest;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import restTest.ani.entity.Animation;
import restTest.ani.entity.Company;
import restTest.ani.repository.AnimationRepository;
import restTest.ani.repository.CompanyRepository;

@SpringBootTest
class RestTestApplicationTests {
	
	@Autowired
	private AnimationRepository aniRepo;
	
	@Autowired
	private CompanyRepository comRepo;

	@Transactional
	@Test
	@Commit
	void contextLoads() {
		
		IntStream.rangeClosed(1, 10).forEach(i -> {
			
			Company com = Company.builder().name("com" + i).build();
			
			com.addList(Animation.builder().title("aniTitle1_" + i).category("category1_" + i).company(com).content("content1").build());
			com.addList(Animation.builder().title("aniTitle2_" + i).category("category2_" + i).company(com).content("content2").build());
			
			comRepo.save(com);
		});
		
		IntStream.rangeClosed(1, 10).forEach(i -> {
			Company com = Company.builder().id((long) i).build();
			Animation ani1 = Animation.builder().title("aniTitle1_" + i).category("category1_" + i).company(com).content("content1").build();
			Animation ani2 = Animation.builder().title("aniTitle2_" + i).category("category2_" + i).company(com).content("content2").build();
			aniRepo.save(ani1);
			aniRepo.save(ani2);
		});
	}

	@Transactional
	@Test
	void findAni() {
		
		aniRepo.findAll().forEach(System.out::println);
		System.out.println("=========================");
		comRepo.findAll().forEach(System.out::println);
	}
}
