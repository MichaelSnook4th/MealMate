package com.mealmate.servlets;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mealmate.beans.User;
import com.mealmate.dao.EmailUtility;
import com.mealmate.dao.UserDAO;


@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("registration.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Retrieve selected categories
        String[] selectedCategories = request.getParameterValues("categories");

        // Create a new User object
        User user = new User(firstName, lastName, address, email, password);

        // Register the user in the database
        try {
            userDAO.registerUser(user, selectedCategories);

            // Generate and store a verification token
            String token = UUID.randomUUID().toString();
            userDAO.storeToken(user.getUserId(), token);

            // Send a verification email to the user
            EmailUtility.sendVerificationEmail(email, token);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Redirect to login page
        response.sendRedirect("login.jsp");
    }


}
