<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        .shopping-list {
            list-style-type: none;
            padding-left: 0;
        }
        .shopping-list li {
            background-color: #f2f2f2;
            margin: 5px 0;
            padding: 10px;
            border-radius: 4px;
        }
        .buttons {
            margin-top: 20px;
        }
        .buttons button {
            padding: 10px 20px;
            margin-right: 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .buttons button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Your Shopping List</h2>
        <ul class="shopping-list">
            <%
                List<String> shoppingList = (List<String>) session.getAttribute("shoppingList");
                if (shoppingList != null && !shoppingList.isEmpty()) {
                    for (String item : shoppingList) {
            %>
                        <li><%= item %></li>
            <%
                    }
                } else {
                    out.println("<li>No items in your shopping list</li>");
                }
            %>
        </ul>
        <div class="buttons">
            <button onclick="location.href='showIngredients.jsp'">Back to Ingredients</button>
            <button onclick="location.href='welcome.jsp'">Back to Welcome</button>
        </div>
    </div>
</body>
</html>
