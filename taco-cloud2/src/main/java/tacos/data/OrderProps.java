package tacos.data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Component
// Spring Boot 에서 properties 파일에 정의된 프로퍼티 중 주어진 prefix 를 가지는 프로퍼티들을 POJO 에 매핑하여 Bean 으로 만들수 있게 해주는 어노테이션
// *.properties , *.yml 파일에 있는 property를 자바 클래스에 값을 가져와서(바인딩) 사용할 수 있게 해주는 어노테이션
@ConfigurationProperties(prefix = "taco.orders")
@Validated // Validation 애노테이션이 적용 가능한 클래스
public class OrderProps {

	@Min(value=5, message="must be between 5 and 25")
	@Max(value=25, message="must be between 5 and 25")
	private int pageSize = 20;
}
