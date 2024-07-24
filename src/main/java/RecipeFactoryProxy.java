public class RecipeFactoryProxy {
	private RecipeFactory recipeFactory;
	
	public RecipeFactoryProxy() {
		this.recipeFactory = new RecipeFactory();
	}

	public Recipe getRecipeName(String recipeName) {
		if (recipeName == null || recipeName.isEmpty()) {
			return null;
		}
		return recipeFactory.createRecipe(recipeName);
	}
}
