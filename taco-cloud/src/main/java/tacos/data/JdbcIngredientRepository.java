package tacos.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import tacos.Ingredient;

@Repository
@RequiredArgsConstructor
public class JdbcIngredientRepository implements IngredientRepository {

	private final JdbcTemplate jdbc;
	
	@Override
	public Iterable<Ingredient> findAll() {
		return jdbc.query("select id, name, type from Ingredient",
				(ResultSet rs, int rowNum) -> this.mapRowToIngredient(rs, rowNum)); // = this::mapRowToIngredient
	}
	
	
	@Override
	public Ingredient findById(String id) {
		return jdbc.queryForObject(
				"select id, name, type from Ingredient where id=?",
				this::mapRowToIngredient, id);
	}
	
	private Ingredient mapRowToIngredient(ResultSet rs, int rowNum)
			throws SQLException {
		System.out.println("rowNum : " + rowNum);
		return new Ingredient(
				rs.getString("id"),
				rs.getString("name"),
				Ingredient.Type.valueOf(rs.getString("type")));
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		jdbc.update(
				"insert into Ingredient (id, name, type) values (?, ?, ?)",
				ingredient.getId(),
				ingredient.getName(),
				ingredient.getType().toString());
		return ingredient;
	}
}
