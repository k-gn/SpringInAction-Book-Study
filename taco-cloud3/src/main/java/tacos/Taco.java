package tacos;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity // JPA 개체로 선언
public class Taco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // 데이터 베이스가 자동으로 생성 전략 지정
	private Long id;
	
	private Date createdAt;
	
	@NotNull
	@Size(min=5, message="Name must be at least 5 characters long")
	private String name;
	
	@Size(min=1, message="You must choose at least 1 ingredient")
	@ManyToMany(targetEntity = Ingredient.class) // targetEntity : 연결을 맺는 상대 엔티티, 다대다 관계 (한 타코에 여러 식자재, 한 식자재는 여러 타코에 사용)
	private List<Ingredient> ingredients; // Converter 를 통해서 id 값이 Ingredient 로 변환된다.
	
	@PrePersist // Taco 객체가 저장되기 전에 자동으로 현재 시간 설정
	void createdAt() {
		this.createdAt = new Date();
	}

}

/*
 * 기본키 자동생성 방법 
 * 
 * IDENTITY : 기본 키 생성을 데이터베이스에 위임하는 방법 (데이터베이스에 의존적) 
 * - 주로 MySQL, PostgresSQL, SQL Server, DB2에서 사용합니다.
 * 
 *  SEQUENCE : 데이터베이스 시퀀스를 사용해서 기본 키를 할당하는 방법 (데이터베이스에 의존적) 
 * - 주로 시퀀스를 지원하는 Oracle, PostgresSQL, DB2, H2에서 사용합니다.
 * - @SequenceGenerator를 사용하여 시퀀스 생성기를 등록하고, 실제 데이터베이스의 생성될 시퀀스이름을 지정해줘야 합니다.
 * 
 * TABLE : 키 생성 테이블을 사용하는 방법 
 * - 키 생성 전용 테이블을 하나 만들고 여기에 이름과 값으로 사용할 컬럼을 만드는 방법입니다. 
 * - 테이블을 사용하므로, 데이터베이스 벤더에 상관없이 모든 데이터베이스에 적용이 가능합니다. 
 * 
 * AUTO : 데이터베이스 벤더에 의존하지 않고, 데이터베이스는 기본키를 할당하는 방법
 * - 데이터베이스에 따라서 IDENTITY, SEQUENCE, TABLE 방법 중 하나를 자동으로 선택해주는 방법입니다. 
 * - 예를들어, Oracle일 경우 SEQUENCE를 자동으로 선택해서 사용합니다. 따라서, 데이터베이스를 변경해도 코드를 수정할 필요가 없습니다.
 */
