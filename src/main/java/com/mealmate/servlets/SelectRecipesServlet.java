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
import javax.servlet.http.HttpSession;

import com.mealmate.beans.Recipe;
import com.mealmate.dao.RecipeDAO;
import com.mealmate.service.HtmlGenerator;

@WebServlet("/SelectRecipesServlet")
public class SelectRecipesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String[] selectedRecipes = request.getParameterValues("recipeName");

        if (selectedRecipes != null && selectedRecipes.length > 0) {
            RecipeDAO recipeDAO = new RecipeDAO();
            Set<String> selectedIngredientsSet = new HashSet<>();
            
            List<String> recipeNames = Arrays.asList(selectedRecipes);
            
            for (String recipeName : recipeNames) {
                List<String> ingredients = recipeDAO.getIngredientsByRecipeName(recipeName);
                selectedIngredientsSet.addAll(ingredients);
            }
            
            String recipesHtml = HtmlGenerator.generateRecipesAsTextHtml(recipeNames);
            String ingredientsHtml = HtmlGenerator.generateIngredientsHtml(selectedIngredientsSet);
            
            session.setAttribute("recipesHtml", recipesHtml);
            session.setAttribute("ingredientsHtml", ingredientsHtml);
            session.setAttribute("recipeNames", recipeNames);
        } else {
            session.setAttribute("recipesHtml", "<p>No recipes selected.</p>");
        }

        request.getRequestDispatcher("displayRecipes.jsp").forward(request, response);
    }
}
