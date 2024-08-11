<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <button type="submit" name="update">Update Profile</button>
    </form>

    <h2>Your Profile Details:</h2>
    <ul>
        <li><strong>First Name:</strong> ${user.firstName}</li>
        <li><strong>Last Name:</strong> ${user.lastName}</li>
        <li><strong>Email:</strong> ${user.email}</li>
        <li><strong>Address:</strong> ${user.address}</li>
    </ul>

    <h2>Your Recipes:</h2>
<form action="SelectRecipesServlet" method="post">
    <ul>
        <c:choose>
            <c:when test="${not empty userRecipes}">
                <c:forEach var="recipe" items="${userRecipes}">
                    <li>
                        <input type="checkbox" name="recipeName" value="${recipe.name}" />
                        ${recipe.name}
                    </li>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <li>No recipes available.</li>
            </c:otherwise>
        </c:choose>
    </ul>
    <button type="submit">Submit</button>
</form>
</body>
</html>
