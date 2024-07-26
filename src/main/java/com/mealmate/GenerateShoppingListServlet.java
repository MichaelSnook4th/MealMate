package com.mealmate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/generateShoppingList")
public class GenerateShoppingListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] selectedIngredientsArray = request.getParameterValues("selectedIngredients");
        String[] allIngredientsArray = request.getParameterValues("allIngredients");

        Set<String> selectedIngredients = selectedIngredientsArray != null ? new HashSet<>(Arrays.asList(selectedIngredientsArray)) : new HashSet<>();
        Set<String> allIngredients = new HashSet<>();

        if (allIngredientsArray != null) {
            for (String ingredients : allIngredientsArray) {
                allIngredients.addAll(Arrays.asList(ingredients.split(",")));
            }
        }

        // Automatically select ingredients across all recipes
        Set<String> finalSelectedIngredients = new HashSet<>(selectedIngredients);

        // Identify unselected ingredients
        Set<String> shoppingList = allIngredients.stream()
                .filter(ingredient -> !finalSelectedIngredients.contains(ingredient))
                .collect(Collectors.toSet());

        request.setAttribute("shoppingList", new ArrayList<>(shoppingList));
        request.getRequestDispatcher("shoppingList.jsp").forward(request, response);
    }
}
