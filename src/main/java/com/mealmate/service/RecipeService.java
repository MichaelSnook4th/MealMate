package com.mealmate.service;

import com.mealmate.dao.RecipeDAO;
import com.mealmate.beans.Recipe;
import java.util.List;

public class RecipeService {
    private RecipeDAO recipeDAO;

    public RecipeService() {
        this.recipeDAO = new RecipeDAO();
    }

    public List<Recipe> getRecipesByCategory(String category) {
        return recipeDAO.getRecipesByCategory(category);
    }

    public boolean insertRecipe(String recipeName, String category) {
        return recipeDAO.insertRecipe(recipeName, category);
    }

    public boolean deleteRecipe(String recipeName) {
        return recipeDAO.deleteRecipe(recipeName);
    }

    public boolean updateRecipe(String oldRecipeName, String newRecipeName, String category) {
        return recipeDAO.updateRecipe(oldRecipeName, newRecipeName, category);
    }

    public List<Recipe> getAllRecipes() {
        return recipeDAO.getAllRecipes();
    }

    public List<String> getAllCategories() {
        return recipeDAO.getAllCategories();
    }
}