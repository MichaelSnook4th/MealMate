package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import recipe.Ingredient;

@WebServlet("/UserShoppingListServlet")
public class UserShoppingListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] selectedIngredients = request.getParameterValues("ingredient");
        
        if (selectedIngredients == null) {
            selectedIngredients = new String[0]; 
        }
        
        Set<String> selectedIngredientsSet = new HashSet<>(Arrays.asList(selectedIngredients));
        Set<Ingredient> allIngredients = (Set<Ingredient>) request.getAttribute("allIngredients");

        if (allIngredients == null) {
            allIngredients = new HashSet<>();
        }

        List<Ingredient> missingIngredients = new ArrayList<>();
        
        for (Ingredient ingredient : allIngredients) {
            if (!selectedIngredientsSet.contains(ingredient.getIngredientName())) {
                missingIngredients.add(ingredient);
            }
        }

        for (Ingredient ingredient : missingIngredients) {
            System.out.println("Missing ingredient: " + ingredient.getIngredientName());
        }

        request.setAttribute("missingIngredients", missingIngredients);
        request.getRequestDispatcher("shoppingList.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
