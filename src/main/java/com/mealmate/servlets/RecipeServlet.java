package com.mealmate.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mealmate.beans.Recipe;
import com.mealmate.dao.RecipeDAO;
import com.mealmate.service.HtmlGenerator;

@WebServlet("/RecipeServlet")
public class RecipeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RecipeDAO recipeDAO = RecipeDAO.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String category = request.getParameter("category");
        if (category == null || category.isEmpty()) {
            request.setAttribute("errorMessage", "Category not selected.");
            request.getRequestDispatcher("welcome.jsp").forward(request, response);
            return;
        }

        List<Recipe> recipes = recipeDAO.getRecipesByCategory(category);
        Map<String, List<String>> recipeIngredients = new HashMap<>();

        for (Recipe recipe : recipes) {
            List<String> ingredients = recipeDAO.getIngredientsByRecipeName(recipe.getName());
            recipeIngredients.put(recipe.getName(), ingredients);
        }

        Set<String> allIngredients = recipeIngredients.values().stream().flatMap(List::stream).collect(Collectors.toSet());
        String recipesHtml = HtmlGenerator.generateRecipesWithCheckboxesHtml(new ArrayList<>(recipeIngredients.keySet()));
        String ingredientsHtml = HtmlGenerator.generateIngredientsHtml(allIngredients);

        request.setAttribute("recipesHtml", recipesHtml);
        request.setAttribute("ingredientsHtml", ingredientsHtml);
        request.setAttribute("recipeIngredients", recipeIngredients);

        session.setAttribute("recipeIngredients", recipeIngredients);

        request.getRequestDispatcher("displayRecipes.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
