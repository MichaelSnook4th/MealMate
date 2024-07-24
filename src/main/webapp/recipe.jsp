<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Recipe</title>
    <script type="text/javascript">
        function renderIngredients() {
            var ingredients = ${recipe.ingredients};
            var list = document.getElementById('ingredientList');
            ingredients.forEach(function(ingredientQuantity) {
                var listItem = document.createElement('li');
                listItem.textContent = ingredientQuantity.quantity + 'g of ' + ingredientQuantity.ingredient.ingredientName;
                list.appendChild(listItem);
            });
        }
    </script>
</head>
<body onload="renderIngredients()">
    <h1>${recipe.recipeName}</h1>
    <ul id="ingredientList"></ul>
</body>
</html>
