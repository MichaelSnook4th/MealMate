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
    <%
        List<String> shoppingList = (List<String>) request.getAttribute("shoppingList");
        if (shoppingList != null && !shoppingList.isEmpty()) {
            out.println("<ul>");
            for (String item : shoppingList) {
                out.println("<li>" + item + "</li>");
            }
            out.println("</ul>");
        } else {
            out.println("<p>All ingredients are available.</p>");
        }
    %>
    <!--  <a href="welcome.jsp">Go back to Welcome</a> -->
</body>
</html>