package com.mealmate.service;

import java.util.List;
import java.util.Set;

public class HtmlGenerator {

	private static HtmlGenerator instance;
	
	private HtmlGenerator() {
		
	}
	
	public static synchronized HtmlGenerator getInstance() {
		if (instance == null) {
			instance = new HtmlGenerator();
		}
		return instance;
	}
	public static String generateRecipesAsTextHtml(List<String> recipeNames) {
        StringBuilder recipesHtml = new StringBuilder();
        for (String recipeName : recipeNames) {
            recipesHtml.append("<p>").append(recipeName).append("</p>");
        }
        return recipesHtml.toString();
    }

    public static String generateRecipesWithCheckboxesHtml(List<String> recipeNames) {
        StringBuilder recipesHtml = new StringBuilder();
        recipesHtml.append("<ul>");
        for (String recipeName : recipeNames) {
            recipesHtml.append("<li>")
                .append("<input type='checkbox' name='recipeName' value='")
                .append(recipeName)
                .append("' />")
                .append(recipeName)
                .append("</li>");
        }
        recipesHtml.append("</ul>");
        return recipesHtml.toString();
    }
    
    public static String generateIngredientsHtml(Set<String> ingredientNames) {
        StringBuilder ingredientsHtml = new StringBuilder();
        
        ingredientsHtml.append("<ul>");

        for (String ingredientName : ingredientNames) {
            ingredientsHtml.append("<li>")
                .append("<label>")
                .append("<input type='checkbox' name='selectedIngredients' value='") 
                .append(ingredientName)
                .append("' />")
                .append(ingredientName)
                .append("</label>")
                .append("</li>");
        }
        
        ingredientsHtml.append("</ul>");
        
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
