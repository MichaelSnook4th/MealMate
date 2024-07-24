import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RecipeServlet")
public class RecipeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeFactoryProxy recipeFactoryProxy = new RecipeFactoryProxy();
        Recipe pizza = recipeFactoryProxy.getRecipeName("Pizza");

        request.setAttribute("recipe", pizza);
        request.getRequestDispatcher("recipe.jsp").forward(request, response);
    }
}
