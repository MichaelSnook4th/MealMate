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

@WebServlet("/loginUser")
public class LoginUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/meal_planner", "root", "");

            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("firstname", rs.getString("firstname"));
                session.setAttribute("lastname", rs.getString("lastname"));
                session.setAttribute("email", rs.getString("email"));
                session.setAttribute("address", rs.getString("address"));
                session.setAttribute("categories", rs.getString("categories"));

                
                String categories = rs.getString("categories");
                Map<String, List<String>> categoryRecipes = new HashMap<>();
                List<String> allRecipes = new ArrayList<>();
                for (String category : categories.split(",")) {
                    String recipeQuery = "SELECT recipe_name FROM recipes WHERE category_id = (SELECT category_id FROM categories WHERE category_name = ?)";
                    PreparedStatement recipePs = con.prepareStatement(recipeQuery);
                    recipePs.setString(1, category);
                    ResultSet recipeRs = recipePs.executeQuery();
                    List<String> recipes = new ArrayList<>();
                    while (recipeRs.next()) {
                        String recipe = recipeRs.getString("recipe_name");
                        recipes.add(recipe);
                        allRecipes.add(recipe);
                    }
                    categoryRecipes.put(category, recipes);
                    recipeRs.close();
                    recipePs.close();
                }

                
                Collections.shuffle(allRecipes);
                List<String> randomRecipes = allRecipes.subList(0, Math.min(allRecipes.size(), 5));
                session.setAttribute("categoryRecipes", categoryRecipes);
                session.setAttribute("randomRecipes", randomRecipes);

                response.sendRedirect("welcome.jsp");
            } else {
                request.setAttribute("errorMessage", "Invalid email or password.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
