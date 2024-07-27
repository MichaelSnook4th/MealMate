package servlets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import recipe.HtmlGenerator;
import recipe.Ingredient;
import recipe.Recipe;
import recipe.RecipeFactoryProxy;

@WebServlet("/RecipeServlet")
public class RecipeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] selectedRecipes = request.getParameterValues("recipeName");

        if (selectedRecipes != null && selectedRecipes.length > 0) {
            RecipeFactoryProxy recipeFactoryProxy = new RecipeFactoryProxy();
            List<Recipe> recipes = new ArrayList<>();
            Set<String> selectedIngredientsSet = new HashSet<>();

            for (String recipeName : selectedRecipes) {
                Recipe recipe = recipeFactoryProxy.getRecipeName(recipeName);
                if (recipe != null) {
                    recipes.add(recipe);
                    recipe.getIngredients().forEach(iq -> selectedIngredientsSet.add(iq.getIngredient().getIngredientName()));
                }
            }
            
            String recipesHtml = HtmlGenerator.generateRecipesHtml(recipes);
            String ingredientsHtml = HtmlGenerator.generateIngredientsHtml(selectedIngredientsSet);
            
            request.setAttribute("recipesHtml", recipesHtml);
            request.setAttribute("ingredientsHtml", ingredientsHtml);
        }

        request.getRequestDispatcher("displayRecipes.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

