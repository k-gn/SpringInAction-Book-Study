package tacos;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

// # @WebMvcTest
// 스프링 MVC 애플리케이션의 형태로 테스트가 실행된도록 한다. 또한 스프링 지원을 설정한다.
@WebMvcTest(HomeController.class) // HomeController 웹 페이지 테스트
public class HomeControllerTest {

	// 실제 서버를 시작하는 대신 스프링 MVC의 모의(Mocking) 매커니즘을 사용
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testHomePage() throws Exception {
		
		mockMvc.perform(get("/"))
			// 3가지중 한가지라도 충족하지 않을 경우 테스트 실패
			.andExpect(status().isOk())
			.andExpect(view().name("home"))
			.andExpect(content().string(containsString("Welcome to...")));
	}

}
