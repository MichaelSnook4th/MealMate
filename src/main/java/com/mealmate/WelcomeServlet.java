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

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String categories = (String) session.getAttribute("categories");
        List<String> recipesToShow = new ArrayList<>();
        String[] previouslySelectedRecipes = request.getParameter("recipes") != null 
                ? request.getParameter("recipes").split(",") 
                : new String[0];
        boolean updateFlag = "true".equals(request.getParameter("updateFlag"));

        if (categories != null && !categories.isEmpty()) {
            String[] categoryArray = categories.split(",");
            List<String> allRecipes = new ArrayList<>();
            Map<String, List<String>> categoryRecipes = new HashMap<>();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/meal_planner", "root", "");

                for (String category : categoryArray) {
                    List<String> recipes = new ArrayList<>();
                    String query = "SELECT recipe_name FROM recipes WHERE category_id = (SELECT category_id FROM categories WHERE category_name = ?)";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, category);

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String recipe = rs.getString("recipe_name");
                        recipes.add(recipe);
                        allRecipes.add(recipe);
                    }

                    categoryRecipes.put(category, recipes);

                    rs.close();
                    ps.close();
                }
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (updateFlag || previouslySelectedRecipes.length == 0) {
                Collections.shuffle(allRecipes);
                for (int i = 0; i < Math.min(5, allRecipes.size()); i++) {
                    recipesToShow.add(allRecipes.get(i));
                }
            } else {
                Collections.addAll(recipesToShow, previouslySelectedRecipes);
            }

            request.setAttribute("categoryRecipes", categoryRecipes);
        }

        request.setAttribute("recipesToShow", recipesToShow);
        request.getRequestDispatcher("welcome.jsp").forward(request, response);
    }
}
