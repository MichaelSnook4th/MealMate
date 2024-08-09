<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ingredients</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        .recipe {
            margin-bottom: 20px;
        }
        .ingredients {
            list-style-type: none;
            padding-left: 0;
        }
        .ingredients li {
            background-color: #f2f2f2;
            margin: 5px 0;
            padding: 10px;
            border-radius: 4px;
        }
        .buttons {
            margin-top: 20px;
        }
        .buttons button {
            padding: 10px 20px;
            margin-right: 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .buttons button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Ingredients for Selected Recipes</h2>
        <form action="generateShoppingList" method="post">
            <%
                Map<String, List<String>> recipeIngredients = (Map<String, List<String>>) session.getAttribute("recipeIngredients");
                Set<String> selectedIngredientsSet = (Set<String>) session.getAttribute("selectedIngredientsSet");
                if (selectedIngredientsSet == null) {
                    selectedIngredientsSet = new HashSet<>();
                }
                if (recipeIngredients != null && !recipeIngredients.isEmpty()) {
                    for (Map.Entry<String, List<String>> entry : recipeIngredients.entrySet()) {
                        String recipe = entry.getKey();
                        List<String> ingredients = entry.getValue();
            %>
                        <div class="recipe">
                            <h3><%= recipe %></h3>
                            <ul class="ingredients">
                                <% if (ingredients != null && !ingredients.isEmpty()) {
                                    for (String ingredient : ingredients) { %>
                                        <li>
                                            <label>
                                                <input type="checkbox" name="selectedIngredients" value="<%= ingredient %>" <%= selectedIngredientsSet.contains(ingredient) ? "checked" : "" %>> <%= ingredient %>
                                            </label>
                                            <input type="hidden" name="allIngredients" value="<%= ingredient %>">
                                        </li>
                                <% } } else { %>
                                    <li>No ingredients found</li>
                                <% } %>
                            </ul>
                        </div>
            <%
                    }
                } else {
                    out.println("<p>No ingredients found</p>");
                }
            %>
            <div class="buttons">
                <button type="submit">Generate Shopping List</button>
                <button type="button" onclick="document.getElementById('backForm').submit();">Back to Welcome</button>
            </div>
        </form>
        <form id="backForm" action="showIngredients" method="post">
            <%
                if (selectedIngredientsSet != null) {
                    for (String ingredient : selectedIngredientsSet) {
            %>
                        <input type="hidden" name="selectedIngredients" value="<%= ingredient %>">
            <%
                    }
                }
            %>
        </form>
    </div>
</body>
</html>
