package tacos.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JdbcIngredientRepository implements IngredientRepository {

	private final JdbcTemplate jdbc;
	
	@Override
	public Iterable<Ingredient> findAll() {
		log.info("JdbcIngredientRepository findAll");
		return jdbc.query("select id, name, type from Ingredient",
				(ResultSet rs, int rowNum) -> this.mapRowToIngredient(rs, rowNum)); // = this::mapRowToIngredient
				// RowMapper 를 직접 구현한 것 (꼭 RowMapper 인터페이스의 mapRow를 구현안해도 되는것 같음)
	}
	
	
	@Override
	public Ingredient findById(String id) {
		log.info("JdbcIngredientRepository findById");
		return jdbc.queryForObject(
				"select id, name, type from Ingredient where id=?",
				this::mapRowToIngredient, id);
	}
	
	private Ingredient mapRowToIngredient(ResultSet rs, int rowNum)
			throws SQLException {
//		log.info("mapRowToIngredient : " + rowNum);
		return new Ingredient(
				rs.getString("id"),
				rs.getString("name"),
				Ingredient.Type.valueOf(rs.getString("type")));
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		log.info("JdbcIngredientRepository save");
		jdbc.update(
				"insert into Ingredient (id, name, type) values (?, ?, ?)",
				ingredient.getId(),
				ingredient.getName(),
				ingredient.getType().toString());
		return ingredient;
	}
}
