<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .form-group .checkbox-group {
            display: flex;
            flex-direction: column;
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
        .delete-btn {
            background-color: #dc3545;
            color: white;
        }
        .delete-btn:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Edit Profile</h1>
        <form action="updateProfile" method="post">
            <div class="form-group">
                <label for="firstname">First Name:</label>
                <input type="text" id="firstname" name="firstname" value="<%= request.getAttribute("firstname") %>" required>
            </div>
            <div class="form-group">
                <label for="lastname">Last Name:</label>
                <input type="text" id="lastname" name="lastname" value="<%= request.getAttribute("lastname") %>" required>
            </div>
            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" id="address" name="address" value="<%= request.getAttribute("address") %>" required>
            </div>
            <div class="form-group">
                <label>Favorite Categories:</label>
                <div class="checkbox-group">
                    <%
                        List<String> categories = (List<String>) request.getAttribute("categories");
                        String[] selectedCategories = (String[]) request.getAttribute("selectedCategories");
                        for (String category : categories) {
                            boolean isSelected = Arrays.asList(selectedCategories).contains(category);
                    %>
                    <label>
                        <input type="checkbox" name="categories" value="<%= category %>" <%= isSelected ? "checked" : "" %>> <%= category %>
                    </label>
                    <% } %>
                </div>
            </div>
            <div class="buttons">
                <button type="submit">Update Profile</button>
                <button type="button" class="delete-btn" onclick="if(confirm('Are you sure you want to delete your profile?')) location.href='deleteProfile'">Delete Profile</button>
                <button type="button" onclick="location.href='welcome.jsp'">Back to Welcome</button>
            </div>
        </form>
    </div>
</body>
</html>
