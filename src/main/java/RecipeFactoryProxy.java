public class RecipeFactoryProxy {
	private RecipeFactory recipeFactory;
	
	public RecipeFactoryProxy() {
		this.recipeFactory = new RecipeFactory();
	}

	public Recipe getRecipeName(String recipeName) {
		if (recipeName == null || recipeName.isEmpty()) {
			return null;
		}
		switch (recipeName) {
			case "Pizza":
				return recipeFactory.createPizza();
			default:
				return null;
		}
	}
}
