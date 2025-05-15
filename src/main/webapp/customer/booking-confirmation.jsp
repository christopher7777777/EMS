<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Event" %>
<%
    // Retrieve success message and event object from the request scope
    String successMessage = (String) request.getAttribute("success");
    Event event = (Event) request.getAttribute("event");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking Confirmation</title>
    <!-- Link to external stylesheet -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<!-- Include header content -->
<jsp:include page="/includes/header.jsp" />

<main class="main-content">
    <section class="confirmation-section">
        <div class="container">
            <h1 class="section-title">Booking Confirmation</h1>

            <!-- Display success message if present -->
            <% if (successMessage != null) { %>
            <div class="alert alert-success">
                <%= successMessage %>
            </div>
            <% } %>

            <!-- Display event details if event object is available -->
            <% if (event != null) { %>
            <div class="event-summary">
                <h2>Booked Event: <%= event.getEventTitle() %></h2>
                <div class="summary-detail">
                    <span class="label">Date:</span>
                    <span class="value"><%= event.getEventDate() %></span>
                </div>
                <div class="summary-detail">
                    <span class="label">Location:</span>
                    <span class="value"><%= event.getEventLocation() %></span>
                </div>
                <div class="summary-detail">
                    <span class="label">Price:</span>
                    <span class="value">Rs<%= String.format("%.2f", event.getEventPrice()) %></span>
                </div>
            </div>
            <% } %>

            <!-- Button to navigate back to the events page -->
            <div class="form-buttons">
                <a href="${pageContext.request.contextPath}/events" class="btn btn-primary">Back to Events</a>
            </div>
        </div>
    </section>
</main>

<!-- Include footer content -->
<jsp:include page="/includes/footer.jsp" />

<!-- Link to external JavaScript -->
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
