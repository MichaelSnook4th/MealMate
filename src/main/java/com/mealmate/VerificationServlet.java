package com.mealmate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/verify")
public class VerificationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/meal_planner", "root", "");

            String query = "SELECT * FROM users WHERE token = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String updateQuery = "UPDATE users SET isverified = TRUE, token = NULL WHERE token = ?";
                PreparedStatement updatePs = con.prepareStatement(updateQuery);
                updatePs.setString(1, token);
                int result = updatePs.executeUpdate();

                if (result > 0) {
                    request.setAttribute("successMessage", "Account verified successfully! You can now log in.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Account verification failed.");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }

                updatePs.close();
            } else {
                request.setAttribute("errorMessage", "Invalid verification token.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
