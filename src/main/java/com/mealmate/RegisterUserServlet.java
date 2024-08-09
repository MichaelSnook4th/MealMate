package com.mealmate;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/registerUser")
public class RegisterUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        StringBuilder categories = new StringBuilder();
        String[] selectedCategories = request.getParameterValues("categories");
        if (selectedCategories != null) {
            for (String category : selectedCategories) {
                if (categories.length() > 0) {
                    categories.append(",");
                }
                categories.append(category);
            }
        }

        String verificationToken = generateVerificationToken();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/meal_planner", "root", "");

            String query = "INSERT INTO users (firstname, lastname, address, email, password, categories, token, isverified) VALUES (?, ?, ?, ?, ?, ?, ?, false)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, address);
            ps.setString(4, email);
            ps.setString(5, password);
            ps.setString(6, categories.toString());
            ps.setString(7, verificationToken);

            int result = ps.executeUpdate();
            if (result > 0) {
                EmailUtility.sendVerificationEmail(email, verificationToken);
                request.setAttribute("successMessage", "Profile created successfully! Please check your email to verify your account.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to create profile.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    private String generateVerificationToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[24];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().encodeToString(bytes);
    }
}
