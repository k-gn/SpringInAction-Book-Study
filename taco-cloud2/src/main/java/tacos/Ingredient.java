package tacos;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
// @NoArgsConstructor(force = true) 옵션을 이용해서 final 필드를 0, false, null 등으로 초기화를 강제로 시켜서 생성자를 만들 수 있다.
// AccessLevel : 접근 제한자
// JPA 는 인자가 없는 생성자를 가져야 한다.
// @Data 는 @NoArgsConstructor 가 있으면 인자가 있는 생성자는 제거되기 때문에 인자가 있는 생성자는 따로 추가해야 사용할 수 있다.
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class Ingredient {
	
	@Id
	private final String id;
	
	private final String name;
	
	private final Type type;
	
	public static enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}

}
