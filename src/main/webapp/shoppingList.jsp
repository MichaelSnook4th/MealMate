<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shopping List</title>
</head>
<body>
<h1>Shopping List</h1>
    <%
        String shoppingListHtml = (String) request.getAttribute("shoppingListHtml");
        if (shoppingListHtml != null) {
            out.println(shoppingListHtml);
        } else {
            out.println("<p>No shopping list available.</p>");
        }
    %>
    
    <form action="mealmatehome.jsp" method="get">
        <button type="submit">Back to Home</button>
    </form>
</body>
</html>