<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    HttpSession currentSession = request.getSession(false);
    if (currentSession == null || currentSession.getAttribute("firstname") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String firstname = (String) currentSession.getAttribute("firstname");
    String lastname = (String) currentSession.getAttribute("lastname");
    Map<String, List<String>> categoryRecipes = (Map<String, List<String>>) request.getAttribute("categoryRecipes");
    List<String> recipesToShow = (List<String>) request.getAttribute("recipesToShow");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
</head>
<body>
    <h1>Welcome, <%= firstname %> <%= lastname %>!</h1>
    <a href="logout">Logout</a><br>
    <a href="editUser">Edit Profile</a><br><br>

    <h2>Your Preferred Categories</h2>
    <form action="showRecipes" method="post">
        <%
            if (categoryRecipes != null && !categoryRecipes.isEmpty()) {
                for (Map.Entry<String, List<String>> entry : categoryRecipes.entrySet()) {
                    String category = entry.getKey();
                    List<String> recipes = entry.getValue();
                    %>
                    <h3><%= category %></h3>
                    <%
                        for (String recipe : recipes) {
                            boolean checked = recipesToShow != null && recipesToShow.contains(recipe);
                            %>
                            <input type="checkbox" name="recipes" value="<%= recipe %>" <%= checked ? "checked" : "" %>> <%= recipe %><br>
                            <%
                        }
                    %>
                    <br>
                    <%
                }
            } else {
                out.println("<p>No categories selected</p>");
            }
        %>
        <br>
        <input type="submit" name="action" value="Show Ingredients">
    </form>
</body>
</html>
