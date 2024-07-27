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
	<form action="LogoutServlet" method="post">
		<button type="submit">Logout</button>
	</form>
	<form action="ProfileManagerServlet" method="post">
		<button type="submit">Update Profile</button>
	</form>
	
	
	<p>Please select your favorite meals</p>
	
	<p>${user.categories}</p>

    <form action="SelectRecipesServlet" method="post">
        <label><input type="checkbox" name="recipeName" value="Pizza"> Pizza</label><br>
        <label><input type="checkbox" name="recipeName" value="Nacho"> Nacho</label><br>
        <label><input type="checkbox" name="recipeName" value="Spaghetti Bolognese"> Spaghetti Bolognese</label><br>
        <label><input type="checkbox" name="recipeName" value="Caesar Salad"> Caesar Salad</label><br>
        <label><input type="checkbox" name="recipeName" value="Beef Tacos"> Beef Tacos</label><br>
        <label><input type="checkbox" name="recipeName" value="Beef Stroganof"> Beef Stroganof</label><br>
        <label><input type="checkbox" name="recipeName" value="Chicken Alfredo"> Chicken Alfredo</label><br>
        <button type="submit">Submit</button>
    </form>
</body>
</html>