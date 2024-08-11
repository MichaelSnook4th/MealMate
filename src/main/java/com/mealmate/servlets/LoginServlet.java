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

import com.mealmate.beans.User;
import com.mealmate.beans.Recipe;
import com.mealmate.dao.UserDAO;
import com.mealmate.dao.RecipeDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private RecipeDAO recipeDAO;

    public void init() {
        userDAO = new UserDAO();
        recipeDAO = new RecipeDAO();
    }

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
            User user = userDAO.checkLogin(email, password);
            
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                response.sendRedirect("HomeServlet");
            } 
        } catch (SQLException e) {
            if ("Invalid email or password".equals(e.getMessage())) {
                request.setAttribute("message", "Invalid email or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                throw new ServletException(e);
            }
        }
    }

}
