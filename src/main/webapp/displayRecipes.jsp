<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Selected Recipes</title>
</head>
<body>
    <h1>Selected Recipes</h1>
    <%
        String recipesHtml = (String) request.getAttribute("recipesHtml");
        if (recipesHtml != null) {
            out.println(recipesHtml);
        } else {
            out.println("<p>No recipes selected.</p>");
        }
    %>
    
    <form action="UserShoppingListServlet" method="post">
        <h2>Select Ingredients You Have</h2>
        <%
            String ingredientsHtml = (String) request.getAttribute("ingredientsHtml");
            if (ingredientsHtml != null) {
                out.println(ingredientsHtml);
            } else {
                out.println("<p>No ingredients available.</p>");
            }
        %>
        <button type="submit">Generate Shopping List</button>
    </form>
    
    <form action="mealmatehome.jsp" method="get">
        <button type="submit">Back to Home</button>
    </form>
</body>
</html>
