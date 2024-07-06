import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;



@WebServlet("/DeleteUserServlet")
@MultipartConfig
public class DeleteUserServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	
	public void init() {
		userDAO = new UserDAO();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			try {
				userDAO.deleteUser(user);
			} catch (SQLException e) {
				throw new ServletException(e);
			}
		} else {
			response.sendRedirect("login.jsp");
		}
	}

}
