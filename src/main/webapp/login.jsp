<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login | Event Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth-page">
<div class="auth-container">
    <div class="auth-form-container">
        <div class="auth-logo">
            <a href="${pageContext.request.contextPath}/">
                <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-calendar">
                    <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
                    <line x1="16" y1="2" x2="16" y2="6"></line>
                    <line x1="8" y1="2" x2="8" y2="6"></line>
                    <line x1="3" y1="10" x2="21" y2="10"></line>
                </svg>
                <span>Event Management</span>
            </a>
        </div>

        <h1>Login to Your Account</h1>

        <%
            // Display success message if any
            String successMessage = (String) request.getAttribute("successMessage");
            if (successMessage != null && !successMessage.isEmpty()) {
        %>
        <div class="alert alert-success">
            <%= successMessage %>
        </div>
        <% } %>

        <%
            // Display error message if any
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null && !errorMessage.isEmpty()) {
        %>
        <div class="alert alert-danger">
            <%= errorMessage %>
        </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/login" method="post" class="auth-form">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-buttons">
                <button type="submit" class="btn btn-primary btn-block">Login</button>
            </div>
        </form>

        <div class="auth-links">
            <p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Register Now</a></p>
            <p><a href="${pageContext.request.contextPath}/">Back to Home</a></p>
        </div>
    </div>

    <div class="auth-image">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="100%" height="100%">

        </svg>
        <div class="auth-image-overlay">
            <h2>Welcome Back!</h2>
            <p>Log in to access your events, bookings, and more.</p>
        </div>
    </div>
</div>
</body>
</html>
