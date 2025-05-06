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
    <title>Submit Feedback - <%= event.getEventTitle() %></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <!-- Include Header -->
    <jsp:include page="/includes/header.jsp" />

    <main class="main-content">
        <section class="feedback-section">
            <div class="container">
                <h1 class="section-title">Submit Feedback: <%= event.getEventTitle() %></h1>

                <div class="event-summary">
                    <div class="summary-detail">
                        <span class="label">Date:</span>
                        <span class="value"><%= event.getEventDate() %></span>
                    </div>
                    <div class="summary-detail">
                        <span class="label">Location:</span>
                        <span class="value"><%= event.getEventLocation() %></span>
                    </div>
                </div>

                <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-error">
                    <%= request.getAttribute("error") %>
                </div>
                <% } %>

                <div class="form-container">
                    <form action="${pageContext.request.contextPath}/feedback/submit" method="post" class="feedback-form">
                        <input type="hidden" name="eventId" value="<%= event.getEventId() %>">

                        <div class="form-group">
                            <label>Rating</label>
                            <div class="rating-input">
                                <div class="rating">
                                    <input type="radio" id="star5" name="rating" value="5" required>
                                    <label for="star5">★</label>
                                    <input type="radio" id="star4" name="rating" value="4">
                                    <label for="star4">★</label>
                                    <input type="radio" id="star3" name="rating" value="3">
                                    <label for="star3">★</label>
                                    <input type="radio" id="star2" name="rating" value="2">
                                    <label for="star2">★</label>
                                    <input type="radio" id="star1" name="rating" value="1">
                                    <label for="star1">★</label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="comment">Your Feedback</label>
                            <textarea id="comment" name="comment" rows="5" placeholder="Please share your experience..." required></textarea>
                        </div>

                        <div class="form-buttons">
                            <button type="submit" class="btn btn-primary">Submit Feedback</button>
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
</body>
</html>
