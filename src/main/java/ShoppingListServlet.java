

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ShoppingListServlet")
public class ShoppingListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] selectedIngredients = request.getParameterValues("ingredient");
		List<String> missingIngredients = new ArrayList<>();
		RecipeFactoryProxy recipeFactoryProxy = new RecipeFactoryProxy();
		
		String[] selectedRecipes = request.getParameterValues("recipeName");
		for (String recipeName : selectedRecipes) {
			Recipe recipe = recipeFactoryProxy.getRecipeName(recipeName);
			if (recipe != null) {
				for (IngredientQuantity  iq : recipe.getIngredients()) {
					boolean hasIngredient = false;
					if (selectedIngredients != null) {
						for (String selected : selectedIngredients) {
							if (selected.equals(iq.getIngredient().getIngredientName())) {
								hasIngredient = true;
								break;
							}
						}
					}
					if (!hasIngredient) {
						missingIngredients.add(iq.getIngredient().getIngredientName());
					}
				}
			}
		}
		StringBuilder shoppingListHtml = new StringBuilder();
        shoppingListHtml.append("<h1>Shopping List</h1>");
        if (missingIngredients.isEmpty()) {
            shoppingListHtml.append("<p>You have all the ingredients.</p>");
        } else {
            shoppingListHtml.append("<form action='UserShoppingListServlet' method='post'>");
            shoppingListHtml.append("<ul>");
            for (String ingredient : missingIngredients) {
                shoppingListHtml.append("<li>")
                                .append("<input type='checkbox' name='ingredient' value='")
                                .append(ingredient)
                                .append("' /> ")
                                .append(ingredient)
                                .append("</li>");
            }
            shoppingListHtml.append("</ul>");
            shoppingListHtml.append("<button type='submit'>Submit</button>");
            shoppingListHtml.append("</form>");
        }
        request.setAttribute("shoppingListHtml", shoppingListHtml.toString());		
		request.getRequestDispatcher("shoppingList.jsp").forward(request, response);
	}
}
