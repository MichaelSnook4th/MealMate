package recipe;
import java.util.List;

public class Recipe {
	private String recipeName;
	private List<IngredientQuantity> ingredients;
	
	public Recipe(String recipeName, List<IngredientQuantity> ingredients) {
		this.recipeName = recipeName;
		this.ingredients = ingredients;
	}
	
	public String getRecipeName(){
		return recipeName;
	}
	
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
    
	public List<IngredientQuantity> getIngredients(){
		return ingredients;
	}
 
	public void setIngredients(List<IngredientQuantity> ingredients) {
        this.ingredients = ingredients;
    }
}
