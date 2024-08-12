package com.mealmate.servlets;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mealmate.dao.UserDAO;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	
	public void init() {
		userDAO = UserDAO.getInstance();
	}
       
    public ResetPasswordServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		String newPassword = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		
		if (!newPassword.equals(confirmPassword)) {
			request.setAttribute("message", "Passwords do not match");
			request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
			return;
		}
		
		try {
			boolean isValidToken = userDAO.verifyPasswordRecoveryToken(token);
			if (isValidToken) {
				boolean passwordUpdated = userDAO.updatePasswordWithToken(token, newPassword);
				if (passwordUpdated) {
					userDAO.deletePasswordRecoveryToken(token);
					response.sendRedirect("PasswordResetSuccess.jsp");
				} else {
					request.setAttribute("message", "Failed to update password");
					request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
				}
			} else {
				response.sendRedirect("PasswordRecoveryExpired.jsp");
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

}
