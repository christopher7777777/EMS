<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register | Event Management System</title>
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

        <h1>Create an Account</h1>

        <%
            // Display error message if any
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null && !errorMessage.isEmpty()) {
        %>
        <div class="alert alert-danger">
            <%= errorMessage %>
        </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/register" method="post" class="auth-form">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>

            <div class="form-buttons">
                <button type="submit" class="btn btn-primary btn-block">Register</button>
            </div>
        </form>

        <div class="auth-links">
            <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Login</a></p>
            <p><a href="${pageContext.request.contextPath}/">Back to Home</a></p>
        </div>
    </div>

    <div class="auth-image">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="100%" height="100%">
            <defs>
                <linearGradient id="grad1" x1="0%" y1="0%" x2="100%" y2="100%">
                    <stop offset="0%" style="stop-color:#4CAF50;stop-opacity:1" />
                    <stop offset="100%" style="stop-color:#2196F3;stop-opacity:1" />
                </linearGradient>
            </defs>
            <rect width="100%" height="100%" fill="url(#grad1)"/>
            <g fill="rgba(255,255,255,0.2)">
                <circle cx="4" cy="4" r="2" />
                <circle cx="12" cy="4" r="2" />
                <circle cx="20" cy="4" r="2" />
                <circle cx="4" cy="12" r="2" />
                <circle cx="12" cy="12" r="2" />
                <circle cx="20" cy="12" r="2" />
                <circle cx="4" cy="20" r="2" />
                <circle cx="12" cy="20" r="2" />
                <circle cx="20" cy="20" r="2" />
            </g>
            <path d="M12 2a10 10 0 0 0-10 10c0 1.74.43 3.37 1.17 4.79l1.42-1.43A7.9 7.9 0 0 1 4 12a8 8 0 0 1 16 0 7.9 7.9 0 0 1-.59 3.36l1.42 1.43A9.98 9.98 0 0 0 22 12c0-5.52-4.48-10-10-10zm0 5a5 5 0 0 0-5 5c0 1.11.37 2.12 1 2.95V19h8v-4.05A5 5 0 0 0 17 12a5 5 0 0 0-5-5z" fill="rgba(255,255,255,0.9)"/>
        </svg>
        <div class="auth-image-overlay">
            <h2>Join Us Today!</h2>
            <p>Create an account to start booking events and get personalized recommendations.</p>
        </div>
    </div>
</div>
</body>
</html>
