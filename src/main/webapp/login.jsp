<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user") != null) {
        response.sendRedirect(request.getContextPath() + "/");
        return;
    }
    
    // Check for "remember me" cookie
    String savedUsername = "";
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username")) {
                savedUsername = cookie.getValue();
                break;
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Event Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <!-- Include Header -->
    <jsp:include page="/includes/header.jsp" />
    
    <main class="main-content">
        <div class="auth-container">
            <div class="auth-card">
                <div class="auth-header">
                    <h1 class="auth-title">Login</h1>
                    <p>Welcome back! Please login to your account.</p>
                </div>
                
                <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-error">
                    <%= request.getAttribute("error") %>
                </div>
                <% } %>
                
                <form action="${pageContext.request.contextPath}/login" method="post" class="auth-form">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" value="<%= savedUsername %>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" required>
                    </div>
                    
                    <div class="remember-me">
                        <input type="checkbox" id="rememberMe" name="rememberMe" <%= !savedUsername.isEmpty() ? "checked" : "" %>>
                        <label for="rememberMe">Remember me</label>
                    </div>
                    
                    <div class="form-buttons">
                        <button type="submit" class="btn btn-primary">Login</button>
                    </div>
                </form>
                
                <div class="auth-footer">
                    <p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Register</a></p>
                </div>
            </div>
        </div>
    </main>
    // footer must include
    <!-- Include Footer -->
    <jsp:include page="/includes/footer.jsp" />
    
   <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
