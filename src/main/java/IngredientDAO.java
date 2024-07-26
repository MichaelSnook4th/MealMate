import recipe.Ingredient;

public class IngredientDAO {
	public Ingredient getIngredient(String name) {
		return new Ingredient(name);
	}
}
