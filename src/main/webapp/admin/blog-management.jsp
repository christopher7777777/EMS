<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User, model.Blog, model.Event, java.util.List" %>
<%
    // Check if user is logged in and is admin
    User user = (User) session.getAttribute("user");
    if (user == null || !user.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    List<Blog> blogs = (List<Blog>) request.getAttribute("blogs");
    Blog blogToEdit = (Blog) request.getAttribute("blog");
    List<Event> events = (List<Event>) request.getAttribute("events");
    boolean isEditing = blogToEdit != null;
    String action = (String) request.getAttribute("action");
    if (action == null) action = "list";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blog Management - Event Management System</title>
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
            <li class="active"><a href="${pageContext.request.contextPath}/admin/blogs/list">Manage Blogs</a></li>
            <li><a href="${pageContext.request.contextPath}/profile/edit">Edit Profile</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
        </ul>
    </div>

    <div class="admin-content">
        <div class="admin-header">
            <h1>Blog Management</h1>
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
            <% if (!"add".equals(action) && !"edit".equals(action)) { %>
            <a href="${pageContext.request.contextPath}/admin/blogs/add" class="btn btn-primary">Add New Blog</a>
            <% } %>
        </div>

        <% if ("add".equals(action) || "edit".equals(action)) { %>
        <!-- Blog Form -->
        <div class="form-container">
            <h2><%= isEditing ? "Edit Blog" : "Add New Blog" %></h2>
            <form action="${pageContext.request.contextPath}/admin/blogs/<%= isEditing ? "update" : "add" %>" method="post" enctype="multipart/form-data">
                <% if (isEditing) { %>
                <input type="hidden" name="blogId" value="<%= blogToEdit.getBlogId() %>">
                <% } %>

                <div class="form-group">
                    <label for="blogTitle">Blog Title</label>
                    <input type="text" id="blogTitle" name="blogTitle" value="<%= isEditing ? blogToEdit.getBlogTitle() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="blogDescription">Blog Content</label>
                    <textarea id="blogDescription" name="blogDescription" rows="10" required><%= isEditing ? blogToEdit.getBlogDescription() : "" %></textarea>
                </div>

                <% if (!isEditing) { %>
                <div class="form-group">
                    <label for="eventId">Associated Event</label>
                    <select id="eventId" name="eventId" required>
                        <option value="">Select an Event</option>
                        <% if (events != null) {
                            for (Event event : events) { %>
                        <option value="<%= event.getEventId() %>"><%= event.getEventTitle() %></option>
                        <% } } %>
                    </select>
                </div>
                <% } %>

                <div class="form-group">
                    <label for="blogImage">Blog Image (Optional)</label>
                    <input type="file" id="blogImage" name="blogImage" accept="image/*">
                    <% if (isEditing && blogToEdit.getBlogImage() != null) { %>
                    <p>Current image exists. Uploading a new image will replace it.</p>
                    <% } %>
                </div>

                <div class="form-buttons">
                    <button type="submit" class="btn btn-primary"><%= isEditing ? "Update Blog" : "Add Blog" %></button>
                    <a href="${pageContext.request.contextPath}/admin/blogs/list" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
        <% } else { %>
        <!-- Blogs List -->
        <div class="data-table">
            <% if (blogs != null && !blogs.isEmpty()) { %>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Post Date</th>
                    <th>Image</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <% for (Blog blog : blogs) { %>
                <tr>
                    <td><%= blog.getBlogId() %></td>
                    <td><%= blog.getBlogTitle() %></td>
                    <td><%= blog.getBlogPostDate() %></td>
                    <td>
                        <% if (blog.getBlogImage() != null) { %>
                        Image Present
                        <% } else { %>
                        No Image
                        <% } %>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/blogs/edit?id=<%= blog.getBlogId() %>" class="btn btn-small btn-secondary">Edit</a>
                        <a href="${pageContext.request.contextPath}/admin/blogs/delete?id=<%= blog.getBlogId() %>" class="btn btn-small btn-danger" onclick="return confirm('Are you sure you want to delete this blog?')">Delete</a>
                        <a href="${pageContext.request.contextPath}/blogs/detail?id=<%= blog.getBlogId() %>" class="btn btn-small btn-info" target="_blank">View</a>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <% } else { %>
            <p class="no-data">No blogs found.</p>
            <% } %>
        </div>
        <% } %>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/main.js"/>
</body>
</html>