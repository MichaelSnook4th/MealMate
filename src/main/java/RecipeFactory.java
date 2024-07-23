import java.util.ArrayList;
import java.util.List;

public class RecipeFactory {
	private IngredientDAO ingredientDAO;
	
	public RecipeFactory() {
		this.ingredientDAO = new IngredientDAO();
	}
	
    public Recipe createPizza() {
        Recipe pizza = new Recipe(null, null);
        pizza.setRecipeName("Pizza");

        List<IngredientQuantity> ingredients = new ArrayList<>();

        Ingredient cheese = new Ingredient(null);
        cheese.setIngredientName("Cheese");
        IngredientQuantity cheeseQuantity = new IngredientQuantity(cheese, 0);
        cheeseQuantity.setQuantity(200);
        cheeseQuantity.setIngredient(cheese);
        ingredients.add(cheeseQuantity);

        Ingredient tomatoSauce = new Ingredient(null);
        tomatoSauce.setIngredientName("Tomato Sauce");
        IngredientQuantity tomatoSauceQuantity = new IngredientQuantity(tomatoSauce, 0);
        tomatoSauceQuantity.setQuantity(150);
        tomatoSauceQuantity.setIngredient(tomatoSauce);
        ingredients.add(tomatoSauceQuantity);
        
        Ingredient dough = new Ingredient(null);
        tomatoSauce.setIngredientName("Dough");
        IngredientQuantity doughQuantity = new IngredientQuantity(dough, 0);
        doughQuantity.setQuantity(500);
        doughQuantity.setIngredient(dough);
        ingredients.add(doughQuantity);

        Ingredient peperoni = new Ingredient(null);
        tomatoSauce.setIngredientName("Peperoni");
        IngredientQuantity peperoniQuantity = new IngredientQuantity(peperoni, 0);
        peperoniQuantity.setQuantity(200);
        peperoniQuantity.setIngredient(peperoni);
        ingredients.add(peperoniQuantity);

        pizza.setIngredients(ingredients);

        return pizza;
    }
}
