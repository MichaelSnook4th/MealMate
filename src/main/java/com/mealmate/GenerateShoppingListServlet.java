package com.mealmate;

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

@WebServlet("/generateShoppingList")
public class GenerateShoppingListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] selectedIngredients = request.getParameterValues("selectedIngredients");
        String[] allIngredients = request.getParameterValues("allIngredients");
        HttpSession session = request.getSession(false);
        if (session != null && allIngredients != null) {
            Set<String> selectedIngredientsSet = new HashSet<>(Arrays.asList(selectedIngredients != null ? selectedIngredients : new String[0]));
            session.setAttribute("selectedIngredientsSet", selectedIngredientsSet);

            List<String> unselectedIngredients = new ArrayList<>();
            for (String ingredient : allIngredients) {
                if (!selectedIngredientsSet.contains(ingredient)) {
                    unselectedIngredients.add(ingredient);
                }
            }
            session.setAttribute("shoppingList", unselectedIngredients);
            response.sendRedirect("shoppingList.jsp");
        } else {
            response.sendRedirect("showIngredients.jsp");
        }
    }
}
