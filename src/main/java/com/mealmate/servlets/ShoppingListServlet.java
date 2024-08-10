package com.mealmate.servlets;

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
import javax.servlet.http.HttpSession;

import com.mealmate.service.HtmlGenerator;

@WebServlet("/ShoppingListServlet")
public class ShoppingListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the request
        String[] selectedIngredients = request.getParameterValues("selectedIngredients");
        String[] allIngredients = request.getParameterValues("allIngredients");

        // Check if parameters are null and initialize empty arrays if necessary
        if (selectedIngredients == null) {
            selectedIngredients = new String[0];
        }
        if (allIngredients == null) {
            allIngredients = new String[0];
        }

        // Compute selected ingredients set and unselected ingredients list
        Set<String> selectedIngredientsSet = new HashSet<>(Arrays.asList(selectedIngredients));
        List<String> unselectedIngredients = new ArrayList<>();
        for (String ingredient : allIngredients) {
            if (!selectedIngredientsSet.contains(ingredient)) {
                unselectedIngredients.add(ingredient);
            }
        }

        // Store the results in the session
        HttpSession session = request.getSession();
        session.setAttribute("selectedIngredientsSet", selectedIngredientsSet);
        session.setAttribute("shoppingList", unselectedIngredients);

        // Generate the HTML for the shopping list
        String shoppingListHtml = HtmlGenerator.generateShoppingListHtml(unselectedIngredients);

        // Set the HTML content in the request attributes
        request.setAttribute("shoppingListHtml", shoppingListHtml);

        // Forward to the JSP page to display the shopping list
        request.getRequestDispatcher("shoppingList.jsp").forward(request, response);
    }
}
