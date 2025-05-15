<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Check if user is already logged in
    if (session.getAttribute("user") != null) {
        response.sendRedirect(request.getContextPath() + "/");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Event Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <!-- Include Header -->
    <jsp:include page="/includes/header.jsp" />

    <main class="main-content">
        <div class="auth-container">
            <div class="auth-card">
                <div class="auth-header">
                    <h1 class="auth-title">Register</h1>
                    <p>Create a new account to access all features.</p>
                </div>

                <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-error">
                    <%= request.getAttribute("error") %>
                </div>
                <% } %>

                <form action="${pageContext.request.contextPath}/register" method="post" enctype="multipart/form-data" class="auth-form">
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

                    <div class="form-group">
                        <label for="role">Role</label>
                        <select id="role" name="role" required>
                            <option value="CUSTOMER">Customer</option>
                            <option value="ADMIN">Admin</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="profilePicture">Profile Picture (Optional)</label>
                        <input type="file" id="profilePicture" name="profilePicture" accept="image/*">
                    </div>

                    <div class="form-buttons">
                        <button type="submit" class="btn btn-primary">Register</button>
                    </div>
                </form>

                <div class="auth-footer">
                    <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Login</a></p>
                </div>
            </div>
        </div>
    </main>

    <!-- Include Footer -->
    <jsp:include page="/includes/footer.jsp" />

    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
