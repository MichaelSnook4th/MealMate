package com.mealmate;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mealmate.service.UserService;

@WebServlet("/deleteProfile")
public class DeleteProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    public DeleteProfileServlet() {
        this.userService = new UserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String email = (String) session.getAttribute("email");
            if (userService.deleteUserByEmail(email)) {
                session.invalidate();
                response.sendRedirect("login.jsp");
            } else {
                response.sendRedirect("editProfile.jsp");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
