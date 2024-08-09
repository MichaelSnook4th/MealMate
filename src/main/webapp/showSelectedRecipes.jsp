<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Selected Recipes</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        .recipes {
            list-style-type: none;
            padding-left: 0;
        }
        .recipes li {
            background-color: #f2f2f2;
            margin: 5px 0;
            padding: 10px;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Your Selected Recipes</h2>
        <ul class="recipes">
            <%
                String selectedRecipes = (String) session.getAttribute("selectedRecipes");
                if (selectedRecipes != null && !selectedRecipes.isEmpty()) {
                    List<String> recipesList = Arrays.asList(selectedRecipes.split(","));
                    for (String recipe : recipesList) {
            %>
                        <li><%= recipe %></li>
            <%
                    }
                } else {
                    out.println("<li>No recipes selected</li>");
                }
            %>
        </ul>
    </div>
</body>
</html>
