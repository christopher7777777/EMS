<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
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
    <title>Admin Dashboard - Event Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="admin-container">
        <div class="admin-sidebar">
            <div class="admin-sidebar-header">
                <h2>Admin Panel</h2>
            </div>
            <ul class="admin-menu">
                <li class="active"><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/events/list">Manage Events</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/users/list">Manage Users</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/feedback">View Feedback</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/contacts">View Messages</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/blogs/list">Manage Blogs</a></li>
                <li><a href="${pageContext.request.contextPath}/profile/edit">Edit Profile</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>

            </ul>
        </div>
        
        <div class="admin-content">
            <div class="admin-header">
                <h1>Dashboard</h1>
                <div class="admin-user">
                    <span>Welcome, <%= user.getUsername() %></span>
                </div>
            </div>
            
            <div class="dashboard-stats">
                <div class="stat-card">
                    <div class="stat-value"><%= request.getAttribute("totalEvents") %></div>
                    <div class="stat-label">Events</div>
                </div>
                <div class="stat-card">
                    <div class="stat-value"><%= request.getAttribute("totalBookings") %></div>
                    <div class="stat-label">Bookings</div>
                </div>
                <div class="stat-card">
                    <div class="stat-value"><%= request.getAttribute("totalUsers") %></div>
                    <div class="stat-label">Users</div>
                </div>
                <div class="stat-card">
                    <div class="stat-value"><%= request.getAttribute("totalCustomers") %></div>
                    <div class="stat-label">Customers</div>
                </div>
                <div class="stat-card">
                    <div class="stat-value"><%= request.getAttribute("totalFeedback") %></div>
                    <div class="stat-label">Feedback</div>
                </div>
                <div class="stat-card">
                    <div class="stat-value"><%= request.getAttribute("totalContacts") %></div>
                    <div class="stat-label">Messages</div>
                </div>
                <div class="stat-card">
                    <div class="stat-value"><%= request.getAttribute("totalBlogs") %></div>
                    <div class="stat-label">Blogs</div>
                </div>
            </div>
            
            <div class="dashboard-actions">
                <h2>Quick Actions</h2>
                <div class="action-buttons">
                    <a href="${pageContext.request.contextPath}/admin/events/add" class="btn btn-primary">Add New Event</a>
                    <a href="${pageContext.request.contextPath}/admin/blogs/add" class="btn btn-secondary">Post New Blog</a>
                    <a href="${pageContext.request.contextPath}/admin/contacts" class="btn btn-info">View Messages</a>
                    <a href="${pageContext.request.contextPath}/admin/bookings/*" class="btn btn-info">View Booking</a>
                </div>
            </div>
        </div>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
