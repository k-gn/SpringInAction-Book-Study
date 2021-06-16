package tacos.data;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Taco;

@Repository
@Slf4j
public class JdbcTacoRepository implements TacoRepository {

	private JdbcTemplate jdbc;
	public JdbcTacoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		log.info("tacoId : " + tacoId);
		taco.setId(tacoId);
		for (Ingredient ingredient : taco.getIngredients()) {
			saveIngredientToTaco(ingredient, tacoId);
		}
		return taco;
	}
	
	private long saveTacoInfo(Taco taco) {
		taco.setCreatedAt(new Date());
		// jdbc.update 에서 PreparedStatement 를 활용하여 SQL을 실행하고 싶을 때 PreparedStatementCreator 로 처리
		PreparedStatementCreator psc =
				new PreparedStatementCreatorFactory( // PreparedStatementCreator 객체를 만들 때 도와주는 클래스
						"insert into Taco (name, createdAt) values (?, ?)", // 실행할 INSERT SQL 
						Types.VARCHAR, Types.TIMESTAMP // 해당 INSERT 값의 타입
						).newPreparedStatementCreator(
								Arrays.asList( // 리스트 형태로 ? 값 지정
										taco.getName(),
										new Timestamp(taco.getCreatedAt().getTime())));
		// update() 메소드는 변경된 행의 개수만 리턴하므로 자동 생성 된 키 값을 알아낼 수 없다. 이럴 땐 KeyHolder를 사용해야 한다
		// 자동생성된 id를 저장하고 long 타입으로 반환
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(psc, keyHolder);
		return keyHolder.getKey().longValue();
	}
	
	private void saveIngredientToTaco(
			Ingredient ingredient, long tacoId) {
		jdbc.update(
				"insert into Taco_Ingredients (taco, ingredient) " +
						"values (?, ?)",
						tacoId, ingredient.getId());
	}
}
