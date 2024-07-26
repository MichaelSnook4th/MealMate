package servlets;


import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmailUtility;
import dao.UserDAO;
import user.User;

/**
 * Servlet implementation class PasswordRecoveryServlet
 */
@WebServlet("/PasswordRecoveryServlet")
public class PasswordRecoveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	
	public void init() {
		userDAO = new UserDAO();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordRecoveryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		
		try {
			boolean isValidToken = userDAO.verifyPasswordRecoveryToken(token);
			if (isValidToken) {
				request.setAttribute("token", token);
				request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
			} else {
				response.sendRedirect("PasswordRecoveryExpired.jsp");
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		
		try {
			User user = userDAO.getUserByEmail(email);
			if (user != null) {
				String token = UUID.randomUUID().toString();
				userDAO.storePasswordRecoveryToken(user.getUserId().toString(), token);
				EmailUtility.sendPasswordRecoveryEmail(email, token);
				response.sendRedirect("PasswordRecoveryConfirmation.jsp");
			} else {
				request.setAttribute("message", "Email address not found");
				request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

}
