<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Event, model.User" %>
<%
    Event event = (Event) request.getAttribute("event");
    if (event == null) {
        response.sendRedirect(request.getContextPath() + "/events");
        return;
    }
    
    User user = (User) session.getAttribute("user");
    boolean isLoggedIn = user != null;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= event.getEventTitle() %> - Event Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <!-- Include Header -->
    <jsp:include page="/includes/header.jsp" />
    
    <main class="main-content">
        <section class="event-detail-section">
            <div class="container">
                <div class="event-detail-header">
                    <h1 class="event-title"><%= event.getEventTitle() %></h1>
                    <span class="event-price">$<%= String.format("%.2f", event.getEventPrice()) %></span>
                </div>
                
                <div class="event-details">
                    <div class="event-info">
                        <p class="event-date"><strong>Date:</strong> <%= event.getEventDate() %></p>
                        <p class="event-location"><strong>Location:</strong> <%= event.getEventLocation() %></p>
                    </div>
                    
                    <div class="event-description">
                        <h2>Description</h2>
                        <div class="description-content">
                            <p><%= event.getEventDescription() %></p>
                        </div>
                    </div>
                    
                    <div class="event-actions">
                        <% if (isLoggedIn) { %>
                        <a href="${pageContext.request.contextPath}/booking/form?eventId=<%= event.getEventId() %>" class="btn btn-primary">Book Now</a>
                        <a href="${pageContext.request.contextPath}/feedback/form?eventId=<%= event.getEventId() %>" class="btn btn-secondary">Submit Feedback</a>
                        <% } else { %>
                        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">Login to Book</a>
                        <% } %>
                        <a href="${pageContext.request.contextPath}/events" class="btn btn-outline">Back to Events</a>
                    </div>
                </div>
            </div>
        </section>
    </main>
    
    <!-- Include Footer -->
    <jsp:include page="/includes/footer.jsp" />
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
