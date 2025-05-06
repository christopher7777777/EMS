<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User, java.util.List, java.util.Base64" %>
<%
    // Check if user is logged in and is admin
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null || !currentUser.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    List<User> users = (List<User>) request.getAttribute("users");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Users - Event Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="admin-container">
    <div class="admin-sidebar">
        <div class="admin-sidebar-header">
            <h2>Admin Panel</h2>
        </div>
        <ul class="admin-menu">
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/events/list">Manage Events</a></li>
            <li class="active"><a href="${pageContext.request.contextPath}/admin/users/list">Manage Users</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/feedback">View Feedback</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/contacts">View Messages</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/blogs/list">Manage Blogs</a></li>
            <li><a href="${pageContext.request.contextPath}/profile/edit">Edit Profile</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
        </ul>
    </div>

    <div class="admin-content">
        <div class="admin-header">
            <h1>Manage Users</h1>
            <div class="admin-user">
                <span>Welcome, <%= currentUser.getUsername() %></span>
            </div>
        </div>

        <% if (request.getAttribute("success") != null) { %>
        <div class="alert alert-success">
            <%= request.getAttribute("success") %>
        </div>
        <% } %>

        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-error">
            <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <!-- Users List -->
        <div class="data-table">
            <% if (users != null && !users.isEmpty()) { %>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Profile</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <% for (User user : users) { %>
                <tr>
                    <td><%= user.getUserId() %></td>
                    <td>
                        <% if (user.getProfilePicture() != null && user.getProfilePicture().length > 0) { %>
                        <img src="data:image/jpeg;base64,<%= Base64.getEncoder().encodeToString(user.getProfilePicture()) %>" alt="<%= user.getUsername() %>" class="user-avatar-small">
                        <% } else { %>
                        <div class="user-avatar-placeholder-small"><%= user.getUsername().substring(0, 1).toUpperCase() %></div>
                        <% } %>
                    </td>
                    <td><%= user.getUsername() %></td>
                    <td><%= user.getEmail() %></td>
                    <td><%= user.getRole() %></td>
                    <td><span class="status-badge <%= user.isActive() ? "active" : "inactive" %>"><%= user.isActive() ? "Active" : "Blocked" %></span></td>
                    <td>
                        <% if (user.getUserId() == currentUser.getUserId()) { %>
                        <span class="current-user-label">Current User</span>
                        <% } else if (user.getRole() == User.Role.CUSTOMER) { %>
                        <% if (user.isActive()) { %>
                        <a href="${pageContext.request.contextPath}/admin/users/block?id=<%= user.getUserId() %>" class="btn btn-small btn-warning">Block</a>
                        <% } else { %>
                        <a href="${pageContext.request.contextPath}/admin/users/unblock?id=<%= user.getUserId() %>" class="btn btn-small btn-success">Unblock</a>
                        <% } %>
                        <a href="${pageContext.request.contextPath}/admin/users/delete?id=<%= user.getUserId() %>" class="btn btn-small btn-danger" onclick="return confirm('Are you sure you want to delete this user?')">Delete</a>
                        <% } else { %>
                        <span class="admin-user-label">Admin User</span>
                        <% } %>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <% } else { %>
            <p class="no-data">No users found.</p>
            <% } %>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>