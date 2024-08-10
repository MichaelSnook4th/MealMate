package com.mealmate.service;

import java.util.List;
import java.util.Set;

public class HtmlGenerator {

    // For displaying recipe names
    public static String generateRecipesHtml(List<String> recipeNames) {
        StringBuilder recipesHtml = new StringBuilder();
        
        for (String recipeName : recipeNames) {
            recipesHtml.append("<h2>").append(recipeName).append("</h2>");
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
