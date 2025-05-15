<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Booking, model.User, java.util.List" %>
<%
    // Check if user is logged in and is admin
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null || !currentUser.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Bookings - Event Management System</title>
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
            <li><a href="${pageContext.request.contextPath}/profile/edit">Edit Profile</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
        </ul>
    </div>

    <div class="admin-content">
        <div class="admin-header">
            <h1>Manage Bookings</h1>
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

        <!-- Bookings List -->
        <div class="data-table">
            <% if (bookings != null && !bookings.isEmpty()) { %>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Event ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Subject</th>
                    <th>Meeting Time</th>
                    <th>Address</th>
                    <th>Booking Date</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <% for (Booking booking : bookings) { %>
                <tr>
                    <td><%= booking.getBookingId() %></td>
                    <td><%= booking.getEventId() %></td>
                    <td><%= booking.getBookingName() %></td>
                    <td><%= booking.getBookingEmail() %></td>
                    <td><%= booking.getBookingPhone() %></td>
                    <td><%= booking.getBookingSubject() %></td>
                    <td><%= booking.getBookingMeetingTime() %></td>
                    <td><%= booking.getBookingAddress() %></td>
                    <td><%= booking.getBookingDate() %></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/bookings/delete?id=<%= booking.getBookingId() %>" class="btn btn-small btn-danger" onclick="return confirm('Are you sure you want to delete this booking?')">Delete</a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <% } else { %>
            <p class="no-data">No bookings found.</p>
            <% } %>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>