<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create User Profile</title>
</head>
<body>
    <h1>Create User Profile</h1>
    <form action="registerUser" method="post">
        <label for="firstname">First Name:</label>
        <input type="text" id="firstname" name="firstname" required><br><br>
        
        <label for="lastname">Last Name:</label>
        <input type="text" id="lastname" name="lastname" required><br><br>
        
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" required><br><br>
        
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <label for="categories">Favorite Categories:</label><br>
        <input type="checkbox" name="categories" value="Indian"> Indian<br>
        <input type="checkbox" name="categories" value="Mexican"> Mexican<br>
        <input type="checkbox" name="categories" value="Chinese"> Chinese<br><br>
        
        <input type="submit" value="Create Profile">
    </form>
</body>
</html>
