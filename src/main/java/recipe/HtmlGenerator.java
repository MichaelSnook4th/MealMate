package recipe;
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
	
	public static String generateIngredientsHtml(Set<String> ingredientNames) {
	    StringBuilder ingredientsHtml = new StringBuilder();
	    
	    for (String ingredientName : ingredientNames) {
	        ingredientsHtml.append("<label>")
	            .append("<input type='checkbox' name='ingredient' value='")
	            .append(ingredientName)
	            .append("' />")
	            .append(ingredientName)
	            .append("</label><br/>");        
	    }
	    
	    return ingredientsHtml.toString();
	}


	
	public static String generateShoppingListHtml(List<String> ingredients) {
        StringBuilder shoppingListHtml = new StringBuilder();
        if (ingredients.isEmpty()) {
            shoppingListHtml.append("<p>No ingredients selected.</p>");
        } else {
            shoppingListHtml.append("<ul>");
            for (String ingredient : ingredients) {
                shoppingListHtml.append("<li>")
                                .append(ingredient)
                                .append("</li>");
            }
            shoppingListHtml.append("</ul>");
        }
        return shoppingListHtml.toString();
    }
}
