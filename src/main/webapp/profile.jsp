<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Profile</title>
</head>
<body>
    <h2>User Profile</h2>
    <c:if test="${not empty user}">
        <p>Welcome, ${user.firstName} ${user.lastName}!</p>
        <form action="ProfileManagerServlet" method="post">
            <div>
                <label>First Name:</label>
                <input type="text" name="firstName" value="${user.firstName}" required>
            </div>
            <div>
                <label>Last Name:</label>
                <input type="text" name="lastName" value="${user.lastName}" required>
            </div>
            <div>
                <label>Address:</label>
                <input type="text" name="address" value="${user.address}" required>
            </div>
            <div>
                <label>Email:</label>
                <input type="text" name="email" value="${user.email}" required>
            </div>
            <div>
                <label>New Password:</label>
                <input type="password" name="password">
            </div>
            <button type="submit" name="update" value="update">Update Profile</button>
            <button type="submit" name="delete" value="delete">Delete Profile</button>
            <a href="mealmatehome.jsp">return</a>
        </form>
    </c:if>
</body>
</html>
