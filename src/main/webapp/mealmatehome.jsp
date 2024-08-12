<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Meal Planner</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <h1>Meal Planner</h1>

    <p>Welcome ${user.firstName} ${user.lastName}!</p>

    <form action="LogoutServlet" method="post">
        <button type="submit">Logout</button>
    </form>

    <form action="ProfileManagerServlet" method="post">
        <button type="submit" name="update">Update Profile</button>
    </form>

    <h2>Your Recipes:</h2>
    <form action="SelectRecipesServlet" method="post">
        ${recipesHtml}
        <button type="submit">Submit</button>
    </form>
</body>
</html>
