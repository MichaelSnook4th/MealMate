

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RecipeServlet")
public class RecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RecipeDAO recipeDAO;
	
	public void init() {
		recipeDAO = new RecipeDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String recipeName = request.getParameter("recipeName");
		Recipe recipe = recipeDAO.getRecipeName(recipeName);
		request.setAttribute("recipe", recipe);
		request.getRequestDispatcher("recipe.jsp").forward(request, response);
	}
}
