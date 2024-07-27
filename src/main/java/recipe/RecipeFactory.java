package recipe;
import java.util.ArrayList;
import java.util.List;

public class RecipeFactory {
	
	public Recipe createRecipe(String recipeName) {
        switch (recipeName) {
            case "Pizza":
                return createPizza();
            case "Nacho":
            	return createNacho();
            case "Spaghetti Bolognese":
            	return createSpaghettiBolognese();
            case "Caesar Salad":
            	return createCaesarSalad();
            case "Beef Tacos":
            	return createBeefTacos();
            case "Beef Stroganof":
            	return createBeefStroganoff();
            case "Chicken Alfredo":
            	return createChickenAlfredo();
            default:
                return null;
        }
    }
	
    public Recipe createPizza() {
        Recipe pizza = new Recipe("Pizza", new ArrayList<>());
        List<IngredientQuantity> ingredients = pizza.getIngredients();
        ingredients.add(new IngredientQuantity(new Ingredient("Mozzerela"), 200));
        ingredients.add(new IngredientQuantity(new Ingredient("Tomato Sauce"), 150));
        ingredients.add(new IngredientQuantity(new Ingredient("Dough"), 500));
        ingredients.add(new IngredientQuantity(new Ingredient("Pepperoni"), 200));
        
        pizza.setIngredients(ingredients);
        return pizza;
    }
    
    public Recipe createNacho() {
        Recipe nacho = new Recipe("Nacho", new ArrayList<>());
        List<IngredientQuantity> ingredients = nacho.getIngredients();
        ingredients.add(new IngredientQuantity(new Ingredient("Nacho Cheese"), 200));
        ingredients.add(new IngredientQuantity(new Ingredient("Tortilla Chips"), 500));
        
        nacho.setIngredients(ingredients);
        return nacho;
    }
    
    public Recipe createSpaghettiBolognese() {
        Recipe spaghettiBolognese = new Recipe("Spaghetti Bolognese", new ArrayList<>());
        List<IngredientQuantity> ingredients = spaghettiBolognese.getIngredients();
        ingredients.add(new IngredientQuantity(new Ingredient("Spaghetti"), 300));
        ingredients.add(new IngredientQuantity(new Ingredient("Ground Beef"), 400));
        ingredients.add(new IngredientQuantity(new Ingredient("Tomato Sauce"), 250));
        ingredients.add(new IngredientQuantity(new Ingredient("Onion"), 100));
        ingredients.add(new IngredientQuantity(new Ingredient("Garlic"), 20));
        ingredients.add(new IngredientQuantity(new Ingredient("Olive Oil"), 30));
        
        spaghettiBolognese.setIngredients(ingredients);
        return spaghettiBolognese;
    }
    
    public Recipe createCaesarSalad() {
        Recipe caesarSalad = new Recipe("Caesar Salad", new ArrayList<>());
        List<IngredientQuantity> ingredients = caesarSalad.getIngredients();
        ingredients.add(new IngredientQuantity(new Ingredient("Romaine Lettuce"), 200));
        ingredients.add(new IngredientQuantity(new Ingredient("Caesar Dressing"), 50));
        ingredients.add(new IngredientQuantity(new Ingredient("Parmesan Cheese"), 30));
        ingredients.add(new IngredientQuantity(new Ingredient("Croutons"), 50));
        ingredients.add(new IngredientQuantity(new Ingredient("Chicken Breast"), 150));
        
        caesarSalad.setIngredients(ingredients);
        return caesarSalad;
    }

    public Recipe createBeefTacos() {
        Recipe beefTacos = new Recipe("Beef Tacos", new ArrayList<>());
        List<IngredientQuantity> ingredients = beefTacos.getIngredients();
        ingredients.add(new IngredientQuantity(new Ingredient("Ground Beef"), 400));
        ingredients.add(new IngredientQuantity(new Ingredient("Taco Shells"), 8));
        ingredients.add(new IngredientQuantity(new Ingredient("Lettuce"), 100));
        ingredients.add(new IngredientQuantity(new Ingredient("Tomato"), 100));
        ingredients.add(new IngredientQuantity(new Ingredient("Cheese"), 100));
        ingredients.add(new IngredientQuantity(new Ingredient("Sour Cream"), 50));
        ingredients.add(new IngredientQuantity(new Ingredient("Taco Sauce"), 50));
        
        beefTacos.setIngredients(ingredients);
        return beefTacos;
    }

    public Recipe createBeefStroganoff() {
        Recipe beefStroganoff = new Recipe("Beef Stroganoff", new ArrayList<>());
        List<IngredientQuantity> ingredients = beefStroganoff.getIngredients();
        ingredients.add(new IngredientQuantity(new Ingredient("Beef Sirloin"), 400));
        ingredients.add(new IngredientQuantity(new Ingredient("Mushrooms"), 200));
        ingredients.add(new IngredientQuantity(new Ingredient("Onion"), 100));
        ingredients.add(new IngredientQuantity(new Ingredient("Sour Cream"), 150));
        ingredients.add(new IngredientQuantity(new Ingredient("Beef Broth"), 200));
        ingredients.add(new IngredientQuantity(new Ingredient("Butter"), 30));
        ingredients.add(new IngredientQuantity(new Ingredient("Garlic"), 20));
        
        beefStroganoff.setIngredients(ingredients);
        return beefStroganoff;
    }

    public Recipe createChickenAlfredo() {
        Recipe chickenAlfredo = new Recipe("Chicken Alfredo", new ArrayList<>());
        List<IngredientQuantity> ingredients = chickenAlfredo.getIngredients();
        ingredients.add(new IngredientQuantity(new Ingredient("Chicken Breast"), 400));
        ingredients.add(new IngredientQuantity(new Ingredient("Fettuccine"), 300));
        ingredients.add(new IngredientQuantity(new Ingredient("Heavy Cream"), 250));
        ingredients.add(new IngredientQuantity(new Ingredient("Parmesan Cheese"), 100));
        ingredients.add(new IngredientQuantity(new Ingredient("Butter"), 30));
        ingredients.add(new IngredientQuantity(new Ingredient("Garlic"), 20));
        ingredients.add(new IngredientQuantity(new Ingredient("Olive Oil"), 30));
        
        chickenAlfredo.setIngredients(ingredients);
        return chickenAlfredo;
    }

}
