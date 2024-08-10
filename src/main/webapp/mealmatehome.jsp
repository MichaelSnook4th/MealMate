<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Meal Planner</title>
</head>
<body>
    <h1>Meal Planner</h1>
    <p>Welcome ${user.firstName} ${user.lastName}!</p>
    <form action="LogoutServlet" method="post">
        <button type="submit">Logout</button>
    </form>
    <form action="ProfileManagerServlet" method="post">
        <button type="submit">Update Profile</button>
    </form>
    
    <p>Your Categories:</p>
    <ul>
        <% 
        // Retrieve the categories from the session
        List<String> categories = (List<String>) session.getAttribute("userCategories");
        
        // Check if categories are not null and not empty
        if (categories != null && !categories.isEmpty()) {
            for (String category : categories) {
                out.println("<li>" + category + "</li>");
            }
        } else {
            out.println("<li>No categories selected.</li>");
        }
        %>
    </ul>
</body>
</html>
