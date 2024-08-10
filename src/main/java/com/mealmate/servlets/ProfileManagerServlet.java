package com.mealmate.servlets;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.mealmate.beans.User;
import com.mealmate.dao.UserDAO;


@WebServlet("/ProfileManagerServlet")
@MultipartConfig
public class ProfileManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");

            if(request.getParameter("update") != null) {
                String password = request.getParameter("password");

                user.setFirstName(request.getParameter("firstName"));
                user.setLastName(request.getParameter("lastName"));
                user.setAddress(request.getParameter("address"));
                user.setEmail(request.getParameter("email"));

                if (password != null && !password.isEmpty()) {
                    user.setPassword(password);
                }

                try {
                    userDAO.updateUser(user);
                    session.setAttribute("user", user);
                    
                    // Fetch user categories and set in session
                    List<String> categories = userDAO.getUserCategories(user.getUserId());
                    session.setAttribute("userCategories", categories);

                    response.sendRedirect("profile.jsp");
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
            } else if(request.getParameter("delete") != null) {
                try {
                    userDAO.deleteUser(user);
                    session.setAttribute("user", null);
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
