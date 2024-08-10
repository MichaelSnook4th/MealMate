package com.mealmate.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String[] selectedRecipes = request.getParameterValues("recipeName");

        if (selectedRecipes == null || selectedRecipes.length == 0) {
            request.setAttribute("errorMessage", "No recipes selected.");
            request.getRequestDispatcher("welcome.jsp").forward(request, response);
            return;
        }

        RecipeDAO recipeDAO = new RecipeDAO();
        Map<String, List<String>> recipeIngredients = new HashMap<>();
        Set<String> selectedIngredientsSet = new HashSet<>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/meal_planner", "root", "")) {
            for (String recipeName : selectedRecipes) {
                List<String> ingredients = new ArrayList<>();
                String query = "SELECT i.ingredient_name FROM ingredients i JOIN recipes r ON i.recipe_id = r.recipe_id WHERE r.recipe_name = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, recipeName);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String ingredient = rs.getString("ingredient_name");
                    ingredients.add(ingredient);
                    selectedIngredientsSet.add(ingredient);
                }
                recipeIngredients.put(recipeName, ingredients);

                rs.close();
                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("welcome.jsp").forward(request, response);
            return;
        }

        String recipesHtml = HtmlGenerator.generateRecipesHtml(new ArrayList<>(recipeIngredients.keySet()));
        String ingredientsHtml = HtmlGenerator.generateIngredientsHtml(selectedIngredientsSet);

        request.setAttribute("recipesHtml", recipesHtml);
        request.setAttribute("ingredientsHtml", ingredientsHtml);
        request.setAttribute("recipeIngredients", recipeIngredients);

        session.setAttribute("recipeIngredients", recipeIngredients);
        session.setAttribute("selectedIngredientsSet", selectedIngredientsSet);

        request.getRequestDispatcher("displayRecipes.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
