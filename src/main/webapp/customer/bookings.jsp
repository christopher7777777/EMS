<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Booking, model.User, java.util.List" %>
<%
    // Check if user is logged in
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null) {
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
    <title>My Bookings - Event Management System</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<!-- Include Header -->
<jsp:include page="/includes/header.jsp" />

<div class="admin-container">
    <div class="admin-content">
        <div class="admin-header">
            <h1>My Bookings History</h1>
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
                </tr>
                </thead>
                <tbody>
                <% for (Booking booking : bookings) { %>
                <tr>
                    <td><%= booking.getBookingId() %></td>
                    <td><%= booking.getEventId() %></td>
                    <td><%= booking.getBookingName() != null ? booking.getBookingName().replace("<", "<").replace(">", ">") : "" %></td>
                    <td><%= booking.getBookingEmail() != null ? booking.getBookingEmail().replace("<", "<").replace(">", ">") : "" %></td>
                    <td><%= booking.getBookingPhone() != null ? booking.getBookingPhone().replace("<", "<").replace(">", ">") : "" %></td>
                    <td><%= booking.getBookingSubject() != null ? booking.getBookingSubject().replace("<", "<").replace(">", ">") : "" %></td>
                    <td><%= booking.getBookingMeetingTime() != null ? booking.getBookingMeetingTime() : "" %></td>
                    <td><%= booking.getBookingAddress() != null ? booking.getBookingAddress().replace("<", "<").replace(">", ">") : "" %></td>
                    <td><%= booking.getBookingDate() != null ? booking.getBookingDate() : "" %></td>
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

<!-- Include Footer -->
<jsp:include page="/includes/footer.jsp" />

<script src="<%= request.getContextPath() %>/js/main.js"></script>
</body>
</html>