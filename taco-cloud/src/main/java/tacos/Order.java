package tacos;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
public class Order {

	private Long id;

	private Date placedAt;

	@NotBlank(message = "Name is required")
	private String deliveryName;

	@NotBlank(message = "Street is required")
	private String deliveryStreet;

	@NotBlank(message = "City is required")
	private String deliveryCity;

	@NotBlank(message = "State is required")
	private String deliveryState;

	@NotBlank(message = "Zip code is required")
	private String deliveryZip;

	@CreditCardNumber(message = "Not a valid credit card number") // 유효한 카드 번호인지 검증하는 어노테이션
	private String ccNumber;

	@Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", // 정규표현식 (MM/YY)
			message = "Must be formatted MM/YY")
	private String ccExpiration;

	@Digits(integer = 3, fraction = 0, message = "Invalid CVV")
	private String ccCVV;

}
/*
 * # Validation
 * 
 * @AssertFalse : false 값만 통과 가능
 * 
 * @AssertTrue : true 값만 통과 가능
 * 
 * @DecimalMax(value=) : 지정된 값 이하의 실수만 통과 가능
 * 
 * @DecimalMin(value=) : 지정된 값 이상의 실수만 통과 가능
 * 
 * @Digits(integer=,fraction=) : 대상 수가 지정된 정수와 소수 자리수보다 적을 경우 통과 가능
 * 
 * @Future : 대상 날짜가 현재보다 미래일 경우만 통과 가능
 * 
 * @Past : 대상 날짜가 현재보다 과거일 경우만 통과 가능
 * 
 * @Max(value) : 지정된 값보다 아래일 경우만 통과 가능
 * 
 * @Min(value) : 지정된 값보다 이상일 경우만 통과 가능
 * 
 * @NotNull : null 값이 아닐 경우만 통과 가능
 * 
 * @Null : null일 겨우만 통과 가능
 * 
 * @Pattern(regex=, flag=) : 해당 정규식을 만족할 경우만 통과 가능
 * 
 * @Size(min=, max=) : 문자열 또는 배열이 지정된 값 사이일 경우 통과 가능
 * 
 * @Valid : 대상 객체의 확인 조건을 만족할 경우 통과 가능
 * 
 */
