package com.mealmate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/meal_planner", "root", "");

            String query = "INSERT INTO users (firstname, lastname, address, email, password, categories) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, address);
            ps.setString(4, email);
            ps.setString(5, password);
            ps.setString(6, categories.toString());

            int result = ps.executeUpdate();
            response.setContentType("text/html");
            if (result > 0) {
                response.getWriter().write("Profile created successfully!<br>");
                response.getWriter().write("<a href='login.jsp'>Go to Login</a>");
            } else {
                response.getWriter().write("Failed to create profile.");
            }

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}
