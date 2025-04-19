<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User, model.Feedback, model.Event, java.util.List, java.util.Map" %>
<%
    // Check if user is logged in and is admin
    User user = (User) session.getAttribute("user");
    if (user == null || !user.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    
    List<Feedback> feedbackList = (List<Feedback>) request.getAttribute("feedbackList");
    Map<Integer, Event> eventMap = (Map<Integer, Event>) request.getAttribute("eventMap");
    List<Event> allEvents = (List<Event>) request.getAttribute("allEvents");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Feedback - Event Management System</title>
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
                <li class="active"><a href="${pageContext.request.contextPath}/admin/feedback">View Feedback</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/contacts">View Messages</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/blogs/list">Manage Blogs</a></li>
                <li><a href="${pageContext.request.contextPath}/profile/edit">Edit Profile</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
        </div>
        
        <div class="admin-content">
            <div class="admin-header">
                <h1>View Feedback</h1>
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
            
            <!-- Filter by Event -->
            <div class="filter-container">
                <form action="${pageContext.request.contextPath}/admin/feedback" method="get">
                    <div class="filter-group">
                        <label for="eventId">Filter by Event:</label>
                        <select id="eventId" name="eventId">
                            <option value="">All Events</option>
                            <% if (allEvents != null) { 
                                for (Event event : allEvents) { %>
                            <option value="<%= event.getEventId() %>"><%= event.getEventTitle() %></option>
                            <% } } %>
                        </select>
                        <button type="submit" class="btn btn-primary">Filter</button>
                    </div>
                </form>
            </div>
            
            <!-- Feedback List -->
            <div class="data-table">
                <% if (feedbackList != null && !feedbackList.isEmpty()) { %>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Event</th>
                            <th>Rating</th>
                            <th>Comment</th>
                            <th>Date</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Feedback feedback : feedbackList) { 
                            Event event = eventMap.get(feedback.getEventId());
                            String eventTitle = event != null ? event.getEventTitle() : "Unknown Event";
                        %>
                        <tr>
                            <td><%= feedback.getFeedbackId() %></td>
                            <td><%= eventTitle %></td>
                            <td>
                                <div class="rating-stars">
                                    <% for (int i = 1; i <= 5; i++) { %>
                                        <span class="star <%= (i <= feedback.getFeedbackRating()) ? "filled" : "" %>">★</span>
                                    <% } %>
                                </div>
                            </td>
                            <td><%= feedback.getFeedbackComment() %></td>
                            <td><%= feedback.getFeedbackDate() %></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/feedback" method="post">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="feedbackId" value="<%= feedback.getFeedbackId() %>">
                                    <button type="submit" class="btn btn-small btn-danger" onclick="return confirm('Are you sure you want to delete this feedback?')">Delete</button>
                                </form>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
                <% } else { %>
                <p class="no-data">No feedback found.</p>
                <% } %>
            </div>
        </div>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
