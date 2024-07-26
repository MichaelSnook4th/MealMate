<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit User Profile</title>
</head>
<body>
    <h1>Edit User Profile</h1>
    <%
        String successMessage = (String) request.getAttribute("successMessage");
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (successMessage != null) {
            out.println("<p style='color:green;'>" + successMessage + "</p>");
        } else if (errorMessage != null) {
            out.println("<p style='color:red;'>" + errorMessage + "</p>");
        }
    %>
    <form action="updateUser" method="post">
        <label for="firstname">First Name:</label>
        <input type="text" id="firstname" name="firstname" value="<%= request.getAttribute("firstname") %>" required><br><br>
        
        <label for="lastname">Last Name:</label>
        <input type="text" id="lastname" name="lastname" value="<%= request.getAttribute("lastname") %>" required><br><br>
        
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="<%= request.getAttribute("address") %>" required><br><br>
        
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="<%= request.getAttribute("email") %>" required readonly><br><br>
        
        <label for="categories">Favorite Categories:</label><br>
        <%
            String categories = (String) request.getAttribute("categories");
            String[] categoryArray = {"Indian", "Mexican", "Chinese"};
            for (String category : categoryArray) {
                boolean checked = categories != null && categories.contains(category);
                %>
                <input type="checkbox" name="categories" value="<%= category %>" <%= checked ? "checked" : "" %>> <%= category %><br>
                <%
            }
        %>
        <br>
        
        <input type="submit" value="Update Profile">
    </form>
    <br>
    <form action="welcome" method="get">
        <input type="hidden" name="updateFlag" value="true">
        <button type="submit">Go back to Welcome</button>
    </form>
</body>
</html>
