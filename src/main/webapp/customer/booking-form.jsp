<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Event, model.User" %>
<%
    // Check if user is logged in
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    
    Event event = (Event) request.getAttribute("event");
    if (event == null) {
        response.sendRedirect(request.getContextPath() + "/events");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Event - <%= event.getEventTitle() %></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <!-- Include Header -->
    <jsp:include page="/includes/header.jsp" />
    
    <main class="main-content">
        <section class="booking-section">
            <div class="container">
                <h1 class="section-title">Book Event: <%= event.getEventTitle() %></h1>
                
                <div class="event-summary">
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
                        <span class="value">$<%= String.format("%.2f", event.getEventPrice()) %></span>
                    </div>
                </div>
                
                <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-error">
                    <%= request.getAttribute("error") %>
                </div>
                <% } %>
                
                <div class="form-container">
                    <form action="${pageContext.request.contextPath}/booking/create" method="post" class="booking-form">
                        <input type="hidden" name="eventId" value="<%= event.getEventId() %>">
                        
                        <div class="form-group">
                            <label for="name">Your Name</label>
                            <input type="text" id="name" name="name" value="<%= user.getUsername() %>" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="phone">Phone Number</label>
                            <input type="tel" id="phone" name="phone" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="subject">Subject</label>
                            <input type="text" id="subject" name="subject" placeholder="Brief meeting purpose" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="meetingTime">Preferred Meeting Time</label>
                            <input type="datetime-local" id="meetingTime" name="meetingTime" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="address">Address</label>
                            <textarea id="address" name="address" rows="3" required></textarea>
                        </div>
                        
                        <div class="form-buttons">
                            <button type="submit" class="btn btn-primary">Confirm Booking</button>
                            <a href="${pageContext.request.contextPath}/events/detail?id=<%= event.getEventId() %>" class="btn btn-secondary">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </main>
    
    <!-- Include Footer -->
    <jsp:include page="/includes/footer.jsp" />
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>
        // Set minimum date for meeting time (current date/time)
        document.addEventListener('DOMContentLoaded', function() {
            const now = new Date();
            now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
            const minDateTime = now.toISOString().slice(0, 16);
            document.getElementById('meetingTime').min = minDateTime;
            
            // Set default time to event date + 9:00 AM
            const eventDate = '<%= event.getEventDate() %>';
            if (eventDate) {
                const defaultDateTime = eventDate + 'T09:00';
                document.getElementById('meetingTime').value = defaultDateTime;
            }
        });
    </script>
</body>
</html>
