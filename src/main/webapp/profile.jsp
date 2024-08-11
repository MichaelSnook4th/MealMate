<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Profile</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <h2>User Profile</h2>
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
    
	<label for="categories">Favorite Categories:</label><br>
<input type="checkbox" name="categories" value="1" ${userCategories.contains('Indian') ? 'checked' : ''}> Indian<br>
<input type="checkbox" name="categories" value="2" ${userCategories.contains('Mexican') ? 'checked' : ''}> Mexican<br>
<input type="checkbox" name="categories" value="3" ${userCategories.contains('Chinese') ? 'checked' : ''}> Chinese<br><br>


        
    <button type="submit" name="update" value="update">Update Profile</button>
    <button type="submit" name="delete" value="delete">Delete Profile</button>
</form>
<form action="HomeServlet" method="get">
    <button type="submit">Back to Home</button>
</form>
</body>
</html>