<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Selected Recipes</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h1>Selected Recipes</h1>
        <div class="left-align">
            ${recipesHtml}
        </div>

        <h2>Select Missing Ingredients</h2>
        <form action="ShoppingListServlet" method="post" class="left-align">
            ${ingredientsHtml}
            <button type="submit">Generate Shopping List</button>
        </form>

        <form action="HomeServlet" method="get" class="left-align">
            <button type="submit">Back to Home</button>
        </form>
    </div>
</body>
</html>
