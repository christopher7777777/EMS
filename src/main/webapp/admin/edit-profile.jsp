<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User, java.util.Base64" %>
<%
    // Check if user is logged in and is admin
    User user = (User) session.getAttribute("user");
    if (user == null || !user.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Profile - Event Management System</title>
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
                <li><a href="${pageContext.request.contextPath}/admin/users/list">Manage Users</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/feedback">View Feedback</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/contacts">View Messages</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/blogs/list">Manage Blogs</a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/profile/edit">Edit Profile</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
        </div>
        
        <div class="admin-content">
            <div class="admin-header">
                <h1>Edit Profile</h1>
                <div class="admin-user">
                    <span>Welcome, <%= user.getUsername() %></span>
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
            
            <div class="profile-container">
                <div class="profile-card">
                    <div class="profile-image">
                        <% if (user.getProfilePicture() != null && user.getProfilePicture().length > 0) { %>
                        <img src="data:image/jpeg;base64,<%= Base64.getEncoder().encodeToString(user.getProfilePicture()) %>" alt="<%= user.getUsername() %>">
                        <% } else { %>
                        <div class="profile-placeholder"><%= user.getUsername().substring(0, 1).toUpperCase() %></div>
                        <% } %>
                    </div>
                    
                    <div class="profile-info">
                        <h2><%= user.getUsername() %></h2>
                        <p><%= user.getEmail() %></p>
                        <p>Role: <%= user.getRole() %></p>
                    </div>
                </div>
                
                <div class="profile-tabs">
                    <button class="tab-button active" data-target="edit-profile">Edit Profile</button>
                    <button class="tab-button" data-target="change-password">Change Password</button>
                </div>
                
                <div class="tab-content" id="edit-profile">
                    <h3>Edit Profile Information</h3>
                    <form action="${pageContext.request.contextPath}/profile/update" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input type="text" id="username" name="username" value="<%= user.getUsername() %>" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="profilePicture">Profile Picture</label>
                            <input type="file" id="profilePicture" name="profilePicture" accept="image/*">
                            <small>Leave empty to keep current picture</small>
                        </div>
                        
                        <div class="form-buttons">
                            <button type="submit" class="btn btn-primary">Update Profile</button>
                        </div>
                    </form>
                </div>
                
                <div class="tab-content hidden" id="change-password">
                    <h3>Change Password</h3>
                    <form action="${pageContext.request.contextPath}/profile/changePassword" method="post">
                        <div class="form-group">
                            <label for="currentPassword">Current Password</label>
                            <input type="password" id="currentPassword" name="currentPassword" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="newPassword">New Password</label>
                            <input type="password" id="newPassword" name="newPassword" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="confirmPassword">Confirm New Password</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" required>
                        </div>
                        
                        <div class="form-buttons">
                            <button type="submit" class="btn btn-primary">Change Password</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>
        // Tab functionality
        document.addEventListener('DOMContentLoaded', function() {
            const tabButtons = document.querySelectorAll('.tab-button');
            const tabContents = document.querySelectorAll('.tab-content');
            
            tabButtons.forEach(button => {
                button.addEventListener('click', function() {
                    // Remove active class from all buttons
                    tabButtons.forEach(btn => btn.classList.remove('active'));
                    
                    // Add active class to clicked button
                    this.classList.add('active');
                    
                    // Hide all tab contents
                    tabContents.forEach(content => content.classList.add('hidden'));
                    
                    // Show the target tab content
                    const targetId = this.getAttribute('data-target');
                    document.getElementById(targetId).classList.remove('hidden');
                });
            });
        });
    </script>
</body>
</html>
