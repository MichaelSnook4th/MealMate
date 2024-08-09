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

@WebServlet("/updateProfile")
public class UpdateProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String email = (String) session.getAttribute("email");
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String address = request.getParameter("address");
            String[] categoriesArray = request.getParameterValues("categories");
            String categories = String.join(",", categoriesArray);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/meal_planner", "root", "");

                String query = "UPDATE users SET firstname = ?, lastname = ?, address = ?, categories = ? WHERE email = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, firstname);
                ps.setString(2, lastname);
                ps.setString(3, address);
                ps.setString(4, categories);
                ps.setString(5, email);
                ps.executeUpdate();
                ps.close();
                con.close();

                session.setAttribute("firstname", firstname);
                session.setAttribute("lastname", lastname);
                session.setAttribute("address", address);
                session.setAttribute("categories", categories);

                response.sendRedirect("welcome.jsp");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("editProfile.jsp");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
