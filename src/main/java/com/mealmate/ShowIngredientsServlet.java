package com.mealmate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String[] selectedMeals = request.getParameterValues("meals");
        String favoriteFoods = (String) session.getAttribute("favorite_foods");

        List<String> mealsToShow = new ArrayList<>();
        if ("Pick 5 Randomly".equals(action) && favoriteFoods != null) {
            String[] foods = favoriteFoods.split(",");
            List<String> foodsList = new ArrayList<>();
            Collections.addAll(foodsList, foods);
            Collections.shuffle(foodsList);
            for (int i = 0; i < Math.min(5, foodsList.size()); i++) {
                mealsToShow.add(foodsList.get(i));
            }
        } else if (selectedMeals != null) {
            Collections.addAll(mealsToShow, selectedMeals);
        }

        if (mealsToShow.isEmpty()) {
            request.setAttribute("errorMessage", "No meals selected.");
            request.getRequestDispatcher("welcome.jsp").forward(request, response);
            return;
        }

        Map<String, List<String>> mealIngredients = new HashMap<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/meal_planner", "root", "");

            for (String meal : mealsToShow) {
                List<String> ingredients = new ArrayList<>();
                String query = "SELECT i.ingredient_name FROM ingredients i JOIN food_types m ON i.food_id = m.food_id WHERE m.food_name = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, meal);
                
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ingredients.add(rs.getString("ingredient_name"));
                }
                
                mealIngredients.put(meal, ingredients);
                
                rs.close();
                ps.close();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("welcome.jsp").forward(request, response);
            return;
        }

        request.setAttribute("mealIngredients", mealIngredients);
        request.getRequestDispatcher("ingredients.jsp").forward(request, response);
    }
}
