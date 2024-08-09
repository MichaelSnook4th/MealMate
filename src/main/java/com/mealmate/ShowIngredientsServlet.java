package com.mealmate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
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

@WebServlet("/showIngredients")
public class ShowIngredientsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] selectedRecipes = request.getParameterValues("recipes");
        String[] selectedIngredients = request.getParameterValues("selectedIngredients");
        HttpSession session = request.getSession(false);
        if (session != null && selectedRecipes != null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/meal_planner", "root", "");

                Map<String, List<String>> recipeIngredients = new HashMap<>();
                String ingredientQuery = "SELECT ingredient_name FROM ingredients WHERE recipe_id = (SELECT recipe_id FROM recipes WHERE recipe_name = ?)";
                PreparedStatement ps = con.prepareStatement(ingredientQuery);

                for (String recipe : selectedRecipes) {
                    ps.setString(1, recipe);
                    ResultSet rs = ps.executeQuery();
                    List<String> ingredients = new ArrayList<>();
                    while (rs.next()) {
                        ingredients.add(rs.getString("ingredient_name"));
                    }
                    recipeIngredients.put(recipe, ingredients);
                    rs.close();
                }
                ps.close();
                con.close();

                session.setAttribute("recipeIngredients", recipeIngredients);
                if (selectedIngredients != null) {
                    Set<String> selectedIngredientsSet = new HashSet<>(Arrays.asList(selectedIngredients));
                    session.setAttribute("selectedIngredientsSet", selectedIngredientsSet);
                }
                response.sendRedirect("ingredients.jsp");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("welcome.jsp");
            }
        } else {
            response.sendRedirect("welcome.jsp");
        }
    }
}
