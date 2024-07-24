import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SelectRecipesServlet")
public class SelectRecipesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] selectedRecipes = request.getParameterValues("recipeName");
        
        if (selectedRecipes != null && selectedRecipes.length > 0) {
            RecipeFactoryProxy recipeFactoryProxy = new RecipeFactoryProxy();
            List<Recipe> recipes = new ArrayList<>();
            StringBuilder recipesHtml = new StringBuilder();

            for (String recipeName : selectedRecipes) {
                Recipe recipe = recipeFactoryProxy.getRecipeName(recipeName);
                if (recipe != null) {
                    recipes.add(recipe);

                    recipesHtml.append("<h2>").append(recipe.getRecipeName()).append("</h2>")
                               .append("<ul>");

                    for (IngredientQuantity ingredientQuantity : recipe.getIngredients()) {
                        recipesHtml.append("<li>")
                                   .append(ingredientQuantity.getQuantity())
                                   .append("g of ")
                                   .append(ingredientQuantity.getIngredient().getIngredientName())
                                   .append("</li>");
                    }
                    recipesHtml.append("</ul>");
                }
            }

            request.setAttribute("recipesHtml", recipesHtml.toString());
        } else {
            request.setAttribute("recipesHtml", "<p>No recipes selected.</p>");
        }

        request.getRequestDispatcher("displayRecipes.jsp").forward(request, response);
    }
}


