package com.mealmate.servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mealmate.beans.User;
import com.mealmate.dao.UserDAO;

@WebServlet("/ProfileManagerServlet")
public class ProfileManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = UserDAO.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");

            if (request.getParameter("update") != null) {
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String address = request.getParameter("address");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String[] selectedCategories = request.getParameterValues("categories");

                if (firstName != null && !firstName.isEmpty()) user.setFirstName(firstName);
                if (lastName != null && !lastName.isEmpty()) user.setLastName(lastName);
                if (address != null && !address.isEmpty()) user.setAddress(address);
                if (email != null && !email.isEmpty()) user.setEmail(email);
                if (password != null && !password.isEmpty()) user.setPassword(password);

                try {
                    userDAO.updateUser(user);

                    userDAO.updateUserCategories(user.getUserId(), selectedCategories);

                    session.setAttribute("user", user);
                    request.getRequestDispatcher("profile.jsp").forward(request, response);
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
            } else if (request.getParameter("delete") != null) {
                try {
                    userDAO.deleteUser(user);
                    session.invalidate();
                    response.sendRedirect("login.jsp");
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
