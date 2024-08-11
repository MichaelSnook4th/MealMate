<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profile Manager</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h1 {
            color: #333;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            padding: 5px 0;
        }
        button {
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <h1>Profile Manager</h1>

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

    <h2>Your Categories:</h2>
    <ul>
        <c:choose>
            <c:when test="${not empty userCategories}">
                <c:forEach var="category" items="${userCategories}">
                    <li>${category}</li>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <li>No categories selected.</li>
            </c:otherwise>
        </c:choose>
    </ul>
</body>
</html>
