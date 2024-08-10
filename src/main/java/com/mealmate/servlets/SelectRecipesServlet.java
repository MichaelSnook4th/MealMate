package com.mealmate.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mealmate.beans.Recipe;
import com.mealmate.dao.RecipeDAO;
import com.mealmate.service.HtmlGenerator;

@WebServlet("/SelectRecipesServlet")
public class SelectRecipesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] selectedRecipes = request.getParameterValues("recipeName");
        
        if (selectedRecipes != null && selectedRecipes.length > 0) {
            RecipeDAO recipeDAO = new RecipeDAO();
            List<Recipe> recipes = recipeDAO.getAllRecipes();  // Adjust this as needed to fetch recipes
            Set<String> selectedIngredientsSet = new HashSet<>();
            
            List<String> recipeNames = new ArrayList<>();
            List<String> selectedRecipesList = Arrays.asList(selectedRecipes);
            
            for (Recipe recipe : recipes) {
                if (selectedRecipesList.contains(recipe.getName())) {
                    recipeNames.add(recipe.getName());
                }
            }
            
            for (String recipeName : selectedRecipes) {
                List<String> ingredients = recipeDAO.getIngredientsByRecipeName(recipeName);
                selectedIngredientsSet.addAll(ingredients);
            }
            
            String recipesHtml = HtmlGenerator.generateRecipesHtml(recipeNames);
            String ingredientsHtml = HtmlGenerator.generateIngredientsHtml(selectedIngredientsSet);
            
            request.setAttribute("recipesHtml", recipesHtml);
            request.setAttribute("ingredientsHtml", ingredientsHtml);
            request.setAttribute("selectedIngredients", selectedIngredientsSet);
        } else {
            request.setAttribute("recipesHtml", "<p>No recipes selected.</p>");
        }

        request.getRequestDispatcher("displayRecipes.jsp").forward(request, response);
    }
}
