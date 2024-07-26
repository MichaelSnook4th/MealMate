package servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import user.User;

@WebServlet("/ProfileManagerServlet")
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

            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            if(request.getParameter("update") != null) {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setAddress(address);
                user.setEmail(email);

                if (password != null && !password.isEmpty()) {
                    user.setPassword(password);
                }

                try {
                    userDAO.updateUser(user);
                    session.setAttribute("user", user);
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
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect("profile.jsp");
        } else {
            response.sendRedirect("login.jsp");
        }
    }

}
