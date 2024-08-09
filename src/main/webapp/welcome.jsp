<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Arrays" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        .category {
            margin-bottom: 20px;
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
        .logout-btn {
            background-color: #dc3545;
        }
        .logout-btn:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome, <%= session.getAttribute("firstname") %> <%= session.getAttribute("lastname") %></h1>
        <h2>Your Selected Categories and Recipes</h2>
        <form action="showIngredients" method="post">
            <%
                String categories = (String) session.getAttribute("categories");
                Map<String, List<String>> categoryRecipes = (Map<String, List<String>>) session.getAttribute("categoryRecipes");
                List<String> randomRecipes = (List<String>) session.getAttribute("randomRecipes");
                if (categories != null && categoryRecipes != null) {
                    List<String> categoryList = Arrays.asList(categories.split(","));
                    for (String category : categoryList) {
                        List<String> recipes = categoryRecipes.get(category);
            %>
                        <div class="category">
                            <h3><%= category %></h3>
                            <ul class="recipes">
                                <% if (recipes != null) {
                                    for (String recipe : recipes) { %>
                                        <li>
                                            <label>
                                                <input type="checkbox" name="recipes" value="<%= recipe %>" <%= randomRecipes.contains(recipe) ? "checked" : "" %>> <%= recipe %>
                                            </label>
                                        </li>
                                <% } } else { %>
                                    <li>No recipes available</li>
                                <% } %>
                            </ul>
                        </div>
            <%
                    }
                } else {
                    out.println("<p>No categories or recipes found.</p>");
                }
            %>
            <div class="buttons">
                <button type="submit">Show Ingredients</button>
            </div>
        </form>
        <div class="buttons">
            <button onclick="location.href='editProfile'">Edit Profile</button>
            <button class="logout-btn" onclick="location.href='logout'">Logout</button>
        </div>
    </div>
</body>
</html>
