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
import com.mealmate.email.EmailUtility;
import com.mealmate.dao.UserDAO;
import com.mealmate.email.VerificationEmailStrategy;


@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private EmailUtility emailUtility;

    public void init() {
        userDAO = UserDAO.getInstance();
        emailUtility = EmailUtility.getInstance();
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
        String[] categories = request.getParameterValues("categories");

        User user = new User(firstName, lastName, address, email, password);

        try {
            userDAO.registerUser(user, categories);

            String token = UUID.randomUUID().toString();
            userDAO.storeToken(user.getUserId(), token);
            emailUtility.sendEmail(new VerificationEmailStrategy(), email, token);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("login.jsp");
    }

}
