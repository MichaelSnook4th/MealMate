package com.mealmate.servlets;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mealmate.dao.UserDAO;

@WebServlet("/VerifyServlet")
public class VerifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	
	public void init() {
		userDAO = UserDAO.getInstance();
	}
	
    public VerifyServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		
		try {
			boolean isVerified = userDAO.verifyUser(token);
			if (isVerified) {
				response.sendRedirect("VerificationSuccess.jsp");
			} else { 
				response.sendRedirect("VerificationFail.jsp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
