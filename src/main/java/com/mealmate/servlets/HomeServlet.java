package com.mealmate.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mealmate.beans.Recipe;
import com.mealmate.beans.User;
import com.mealmate.dao.RecipeDAO;
import com.mealmate.dao.UserDAO;
import com.mealmate.service.HtmlGenerator;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RecipeDAO recipeDAO;
    private UserDAO userDAO;

    public void init() {
        recipeDAO = new RecipeDAO();
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            try {
                // Fetch user categories
                List<String> categories = userDAO.getUserCategories(user.getUserId());

                // Get the corresponding category IDs and fetch recipes
                List<Integer> categoryIds = recipeDAO.getCategoryIdsByName(categories);
                List<Recipe> recipes = recipeDAO.getRecipesByCategoryIds(categoryIds);

                // Generate HTML for recipes using HtmlGenerator
                String recipesHtml = HtmlGenerator.generateRecipesHtml(
                    recipes.stream().map(Recipe::getName).toList()
                );

                // Set the generated HTML as a request attribute
                request.setAttribute("recipesHtml", recipesHtml);

                // Forward to the JSP page
                request.getRequestDispatcher("mealmatehome.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
