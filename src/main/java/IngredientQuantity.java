
public final class IngredientQuantity {
	private Ingredient ingredient;
	private int quantity;
	
	public IngredientQuantity(Ingredient ingredient, int quantity) {
		this.ingredient = ingredient;
		this.quantity = quantity;
	}
	
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
