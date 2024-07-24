import java.util.ArrayList;
import java.util.List;

public class RecipeFactory {
	
    public Recipe createPizza() {
        Recipe pizza = new Recipe("Pizza", new ArrayList<>());

        List<IngredientQuantity> ingredients = pizza.getIngredients();

        ingredients.add(new IngredientQuantity(new Ingredient("Cheese"), 200));
        ingredients.add(new IngredientQuantity(new Ingredient("Tomato Sauce"), 150));
        ingredients.add(new IngredientQuantity(new Ingredient("Dough"), 500));
        ingredients.add(new IngredientQuantity(new Ingredient("Pepperoni"), 200));

        pizza.setIngredients(ingredients);

        return pizza;
    }
}
