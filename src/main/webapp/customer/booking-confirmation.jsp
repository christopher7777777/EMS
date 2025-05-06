<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Event" %>
<%
    String successMessage = (String) request.getAttribute("success");
    Event event = (Event) request.getAttribute("event");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking Confirmation</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/includes/header.jsp" />

<main class="main-content">
    <section class="confirmation-section">
        <div class="container">
            <h1 class="section-title">Booking Confirmation</h1>

            <% if (successMessage != null) { %>
            <div class="alert alert-success">
                <%= successMessage %>
            </div>
            <% } %>

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

            <div class="form-buttons">
                <a href="${pageContext.request.contextPath}/events" class="btn btn-primary">Back to Events</a>
            </div>
        </div>
    </section>
</main>

<jsp:include page="/includes/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>