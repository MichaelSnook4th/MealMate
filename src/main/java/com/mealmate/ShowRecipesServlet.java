package com.mealmate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/showRecipes")
public class ShowRecipesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String[] selectedRecipes = request.getParameterValues("recipes");

        if (selectedRecipes == null || selectedRecipes.length == 0) {
            request.setAttribute("errorMessage", "No recipes selected.");
            request.getRequestDispatcher("welcome").forward(request, response);
            return;
        }

        Map<String, List<String>> recipeIngredients = new HashMap<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/meal_planner", "root", "");

            for (String recipe : selectedRecipes) {
                List<String> ingredients = new ArrayList<>();
                String query = "SELECT i.ingredient_name FROM ingredients i JOIN recipes r ON i.recipe_id = r.recipe_id WHERE r.recipe_name = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, recipe);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ingredients.add(rs.getString("ingredient_name"));
                }

                recipeIngredients.put(recipe, ingredients);

                rs.close();
                ps.close();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("welcome").forward(request, response);
            return;
        }

        session.setAttribute("recipeIngredients", recipeIngredients);
        request.setAttribute("recipeIngredients", recipeIngredients);
        request.getRequestDispatcher("showIngredients.jsp").forward(request, response);
    }
}
