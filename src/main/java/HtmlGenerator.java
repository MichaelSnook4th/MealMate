import java.util.List;
import java.util.Set;

public class HtmlGenerator {

	public static String generateRecipesHtml(List<Recipe> recipes) {
		StringBuilder recipesHtml = new StringBuilder();
		
		for (Recipe recipe : recipes) {
			recipesHtml.append("<h2>").append(recipe.getRecipeName()).append("</h2>")
                       .append("<ul>");

			for (IngredientQuantity ingredientQuantity : recipe.getIngredients()) {
				recipesHtml.append("<li>")
                .append(ingredientQuantity.getQuantity())
                .append("g of ")
                .append(ingredientQuantity.getIngredient().getIngredientName())
                .append("</li>");
            }
			recipesHtml.append("</ul>");
        }
		return recipesHtml.toString();
	}
	
	public static String generateIngredientsHtml(Set<Ingredient> ingredients) {
		StringBuilder ingredientsHtml = new StringBuilder();
		
		for (Ingredient ingredient : ingredients) {
			ingredientsHtml.append("<label>")
            .append("<input type='checkbox' name='ingredient' value='")
            .append(ingredient.getIngredientName())
            .append("' />")
            .append(ingredient.getIngredientName())
            .append("</label><br/>");		
		}
		
		return ingredientsHtml.toString();
	}
}
