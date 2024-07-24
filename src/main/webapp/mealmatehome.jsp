<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Meal Planner</title>
</head>
<body>
	<h1>Meal Planner</h1>
	<p>Welcome ${user.firstName} ${user.lastName}!</p>
	<p>Please select favourite meals</p>
	<form action="LogoutServlet" method="post">
		<button type="submit">Logout</button>
	</form>
	<form action="ProfileServlet" method="get">
		<button type="submit">Update Profile</button>
	</form>
	<form action="RecipeServlet" method="get">
		<input type="hidden" name="recipeName" value="Pizza">
		<button type="submit">Pizza</button>
	</form>	
</body>
</html>