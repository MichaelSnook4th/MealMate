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
import javax.servlet.http.HttpSession;

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String email = (String) session.getAttribute("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String address = request.getParameter("address");
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

            String query = "UPDATE users SET firstname = ?, lastname = ?, address = ?, categories = ? WHERE email = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, address);
            ps.setString(4, categories.toString());
            ps.setString(5, email);

            int result = ps.executeUpdate();
            if (result > 0) {
                session.setAttribute("firstname", firstname);
                session.setAttribute("lastname", lastname);
                session.setAttribute("categories", categories.toString());
                request.setAttribute("successMessage", "Profile successfully updated!");
            } else {
                request.setAttribute("errorMessage", "Failed to update profile.");
            }

            request.setAttribute("firstname", firstname);
            request.setAttribute("lastname", lastname);
            request.setAttribute("address", address);
            request.setAttribute("email", email);
            request.setAttribute("categories", categories.toString());

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
        }

        request.getRequestDispatcher("editUser.jsp").forward(request, response);
    }
}
