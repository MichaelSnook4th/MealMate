package com.mealmate.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mealmate.beans.User;
import com.mealmate.email.EmailUtility;
import com.mealmate.dao.UserDAO;
import com.mealmate.email.PasswordRecoveryEmailStrategy;

@WebServlet("/PasswordRecoveryServlet")
public class PasswordRecoveryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private EmailUtility emailUtility;

    @Override
    public void init() {
        userDAO = UserDAO.getInstance();
        emailUtility = EmailUtility.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");

        try {
            boolean isValidToken = userDAO.verifyPasswordRecoveryToken(token);
            if (isValidToken) {
                request.setAttribute("token", token);
                request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
            } else {
                response.sendRedirect("PasswordRecoveryExpired.jsp");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error during token verification.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        try {
            User user = userDAO.getUserByEmail(email);
            if (user != null) {
                String token = UUID.randomUUID().toString();
                userDAO.storePasswordRecoveryToken(user.getUserId(), token);
                emailUtility.sendEmail(new PasswordRecoveryEmailStrategy(), email, token);
                response.sendRedirect("PasswordRecoveryConfirmation.jsp");
            } else {
                request.setAttribute("message", "Email address not found");
                request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error during password recovery.", e);
        }
    }
}
