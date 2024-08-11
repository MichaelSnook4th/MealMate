<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping List</title>
</head>
<body>
    <h1>Your Shopping List</h1>
<div>
    ${shoppingListHtml}
</div>
<form action="HomeServlet" method="get">
    <button type="submit">Back to Home</button>
</form>

</body>
</html>
