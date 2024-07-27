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

import recipe.HtmlGenerator;
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

        // Use selectedIngredientsSet directly instead of allIngredients
        String shoppingListHtml = HtmlGenerator.generateShoppingListHtml(new ArrayList<>(selectedIngredientsSet));

        request.setAttribute("shoppingListHtml", shoppingListHtml);
        request.getRequestDispatcher("shoppingList.jsp").forward(request, response);
    }

}
