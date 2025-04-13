<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <link rel="stylesheet" href="../../css/styles.css">
    <title>Register - Event Management System</title>
</head>
<body>
<div class="container">
    <h1>Register</h1>
    <% if (request.getAttribute("error") != null) { %>
    <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>
    <form action="../RegisterController" method="post" class="form">
        <label>
            <input type="email" name="email" placeholder="Email" required>
        </label>
        <label>
            <input type="password" name="password" placeholder="Password" required>
        </label>
        <label>
            <select name="role" required>
                <option value="" disabled selected>Select Role</option>
                <option value="Admin">Admin</option>
                <option value="Customer">Customer</option>
            </select>
        </label>
        <label>
            <input type="text" name="image" placeholder="Profile Image URL (optional)">
        </label>
        <button type="submit">Register</button>
    </form>
    <p>Already have an account? <a href="login.jsp">Login here</a></p>
</div>
</body>
</html>