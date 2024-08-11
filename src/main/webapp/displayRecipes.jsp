<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Selected Recipes</title>
</head>
<body>
    <h1>Selected Recipes</h1>
    <div>
        ${recipesHtml}
    </div>
    <h2>Select Missing Ingredients</h2>
<form action="ShoppingListServlet" method="post">
    ${ingredientsHtml}
    <input type="submit" value="Generate Shopping List">
</form>


</body>
</html>
