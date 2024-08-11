package com.mealmate.servlets;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.mealmate.beans.Recipe;
import com.mealmate.beans.User;
import com.mealmate.dao.RecipeDAO;
import com.mealmate.dao.UserDAO;


@WebServlet("/ProfileManagerServlet")
@MultipartConfig
public class ProfileManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private RecipeDAO recipeDAO;

    public void init() {
        userDAO = new UserDAO();
        recipeDAO = new RecipeDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");

            if(request.getParameter("update") != null) {
                String password = request.getParameter("password");

                user.setFirstName(request.getParameter("firstName"));
                user.setLastName(request.getParameter("lastName"));
                user.setAddress(request.getParameter("address"));
                user.setEmail(request.getParameter("email"));

                if (password != null && !password.isEmpty()) {
                    user.setPassword(password);
                }

                try {
                    userDAO.updateUser(user);

                    List<String> categories = userDAO.getUserCategories(user.getUserId());
                    List<Recipe> recipes = recipeDAO.getAllRecipes(); 

                    System.out.println("User Categories: " + categories);
                    for (Recipe recipe : recipes) {
                        System.out.println("Recipe: " + recipe.getName() + " in category: " + recipe.getCategory());
                    }

                    session.setAttribute("userCategories", categories);
                    request.setAttribute("userRecipes", recipes); 

                    RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
                    dispatcher.forward(request, response);
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
            } else if(request.getParameter("delete") != null) {
                try {
                    userDAO.deleteUser(user);
                    session.setAttribute("user", null);
                    response.sendRedirect("login.jsp");
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            List<String> categories;
            List<Recipe> recipes = new ArrayList<>();
            try {
                categories = userDAO.getUserCategories(user.getUserId());
                
                System.out.println("User Categories: " + categories);

                List<Integer> categoryIds = recipeDAO.getCategoryIdsByName(categories);
                
                System.out.println("Category IDs: " + categoryIds);

                recipes = recipeDAO.getRecipesByCategoryIds(categoryIds);
                
                for (Recipe recipe : recipes) {
                    System.out.println("Filtered Recipe: " + recipe.getName() + " in category: " + recipe.getCategory());
                }

                request.setAttribute("userCategories", categories);
                request.setAttribute("userRecipes", recipes); 
            } catch (SQLException e) {
                throw new ServletException("Error retrieving user categories", e);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }


}
