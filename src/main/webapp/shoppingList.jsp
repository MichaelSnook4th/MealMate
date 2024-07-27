<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping List</title>
</head>
<body>
    <h1>Your Shopping List</h1>
    <ul>
        <%
            List<recipe.Ingredient> missingIngredients = (List<recipe.Ingredient>) request.getAttribute("missingIngredients");
            if (missingIngredients != null && !missingIngredients.isEmpty()) {
                for (recipe.Ingredient ingredient : missingIngredients) {
                    out.println("<li>" + ingredient.getIngredientName() + "</li>");
                }
            } else {
                out.println("<li>No missing ingredients.</li>");
            }
        %>
    </ul>
    <form action="mealmatehome.jsp" method="get">
        <button type="submit">Back to Home</button>
    </form>
</body>
</html>
