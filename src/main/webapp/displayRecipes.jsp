<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Selected Recipes</title>
</head>
<body>
    <h1>Selected Recipes</h1>
    <div>
        <%= request.getAttribute("recipesHtml") %>
    </div>
    
    <h2>Available Ingredients</h2>
    <form action="ShoppingListServlet" method="post">
        <div>
            <%= request.getAttribute("ingredientsHtml") %>
        </div>
        <input type="hidden" name="allIngredients" value="<%= request.getAttribute("allIngredients") %>" />
        <button type="submit">Generate Shopping List</button>
    </form>
    
    <form action="mealmatehome.jsp" method="get">
        <button type="submit">Back to Home</button>
    </form>
</body>
</html>
