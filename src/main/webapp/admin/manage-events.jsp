<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User, model.Event, java.util.List" %>
<%
    // Check if user is logged in and is admin
    User user = (User) session.getAttribute("user");
    if (user == null || !user.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    List<Event> events = (List<Event>) request.getAttribute("events");
    Event eventToEdit = (Event) request.getAttribute("event");
    boolean isEditing = eventToEdit != null;
    String action = request.getParameter("action");
    if (action == null) action = "list";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Events - Event Management System</title>
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
            <li class="active"><a href="${pageContext.request.contextPath}/admin/events/list">Manage Events</a></li>
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
            <h1>Manage Events</h1>
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

        <div class="admin-actions">
            <% if (!isEditing && !"add".equals(action)) { %>
            <a href="${pageContext.request.contextPath}/admin/events/add?action=add" class="btn btn-primary">Add New Event</a>
            <% } %>
        </div>

        <% if (isEditing || (request.getParameter("action") != null && request.getParameter("action").equals("add"))) { %>
        <!-- Event Form -->
        <div class="form-container">
            <h2><%= isEditing ? "Edit Event" : "Add New Event" %></h2>
            <form action="${pageContext.request.contextPath}/admin/events/<%= isEditing ? "update" : "add" %>?action=<%= isEditing ? "update" : "add" %>" method="post" enctype="multipart/form-data">
                <% if (isEditing) { %>
                <input type="hidden" name="eventId" value="<%= eventToEdit.getEventId() %>">
                <% } %>

                <div class="form-group">
                    <label for="eventTitle">Event Title</label>
                    <input type="text" id="eventTitle" name="eventTitle" value="<%= isEditing ? eventToEdit.getEventTitle() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="eventDescription">Event Description</label>
                    <textarea id="eventDescription" name="eventDescription" rows="5" required><%= isEditing ? eventToEdit.getEventDescription() : "" %></textarea>
                </div>

                <div class="form-group">
                    <label for="eventDate">Event Date</label>
                    <input type="date" id="eventDate" name="eventDate" value="<%= isEditing ? eventToEdit.getEventDate() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="eventLocation">Event Location</label>
                    <input type="text" id="eventLocation" name="eventLocation" value="<%= isEditing ? eventToEdit.getEventLocation() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="eventPrice">Event Price (Rs)</label>
                    <input type="number" id="eventPrice" name="eventPrice" step="0.01" value="<%= isEditing ? eventToEdit.getEventPrice() : "" %>" placeholder="Price in Rs" required>
                </div>

                <div class="form-group">
                    <label for="eventImage">Event Image (Optional)</label>
                    <input type="file" id="eventImage" name="eventImage" accept="image/*">
                    <% if (isEditing && eventToEdit.getEventImage() != null) { %>
                    <p>Current image exists. Upload a new image to replace it.</p>
                    <% } %>
                </div>

                <div class="form-buttons">
                    <button type="submit" class="btn btn-primary"><%= isEditing ? "Update Event" : "Add Event" %></button>
                    <a href="${pageContext.request.contextPath}/admin/events/list" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
        <% } else { %>
        <!-- Events List -->
        <div class="data-table">
            <% if (events != null && !events.isEmpty()) { %>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Date</th>
                    <th>Location</th>
                    <th>Price</th>
                    <th>Image</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <% for (Event event : events) { %>
                <tr>
                    <td><%= event.getEventId() %></td>
                    <td><%= event.getEventTitle() %></td>
                    <td><%= event.getEventDate() %></td>
                    <td><%= event.getEventLocation() %></td>
                    <td>Rs <%= String.format("%.2f", event.getEventPrice()) %></td>
                    <td><%= event.getEventImage() != null ? "Image Present" : "No Image" %></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/events/edit?id=<%= event.getEventId() %>" class="btn btn-small btn-secondary">Edit</a>
                        <a href="${pageContext.request.contextPath}/admin/events/delete?id=<%= event.getEventId() %>" class="btn btn-small btn-danger" onclick="return confirm('Are you sure you want to delete this event?')">Delete</a>
                        <a href="${pageContext.request.contextPath}/events/detail?id=<%= event.getEventId() %>" class="btn btn-small btn-info" target="_blank">View</a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <% } else { %>
            <p class="no-data">No events found.</p>
            <% } %>
        </div>
        <% } %>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>