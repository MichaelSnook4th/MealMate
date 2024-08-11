<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ingredients</title>
</head>
<body>
    <h1>Ingredients for Selected Recipes</h1>
    <form action="generateShoppingList" method="post">
        <%
            Map<String, List<String>> recipeIngredients = (Map<String, List<String>>) request.getAttribute("recipeIngredients");
            Set<String> allIngredientsSet = new HashSet<>();
            if (recipeIngredients != null && !recipeIngredients.isEmpty()) {
                for (Map.Entry<String, List<String>> entry : recipeIngredients.entrySet()) {
                    String recipe = entry.getKey();
                    List<String> ingredients = entry.getValue();
                    allIngredientsSet.addAll(ingredients);
                    %>
                    <h2><%= recipe %></h2>
                    <ul>
                        <%
                            for (String ingredient : ingredients) {
                                %>
                                <li>
                                    <input type="checkbox" name="selectedIngredients" value="<%= ingredient %>"> <%= ingredient %>
                                </li>
                                <%
                            }
                        %>
                    </ul>
                    <%
                }
            } else {
                out.println("<p>No ingredients found.</p>");
            }
        %>
        <input type="hidden" name="allIngredients" value="<%= String.join(",", allIngredientsSet) %>">
        <br>
        <button type="submit">Generate Shopping List</button>
    </form>
</body>
</html>
