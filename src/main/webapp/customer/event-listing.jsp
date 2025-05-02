<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Event, java.util.List" %>
<%
    List<Event> events = (List<Event>) request.getAttribute("events");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    Integer totalPages = (Integer) request.getAttribute("totalPages");
    String keyword = (String) request.getAttribute("keyword");
    boolean isSearchResult = request.getAttribute("isSearchResult") != null && (boolean) request.getAttribute("isSearchResult");

    if (currentPage == null) currentPage = 1;
    if (totalPages == null) totalPages = 1;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= isSearchResult ? "Search Results" : "Events" %> - Event Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<!-- Include Header -->
<jsp:include page="/includes/header.jsp" />

<main class="main-content">
    <section class="event-section">
        <div class="container">
            <h1 class="section-title">
                <% if (isSearchResult) { %>
                Search Results for "<%= keyword %>"
                <% } else { %>
                Upcoming Events
                <% } %>
            </h1>

            <!-- Search Form -->
            <div class="search-container">
                <form action="${pageContext.request.contextPath}/events/search" method="get">
                    <input type="text" name="keyword" placeholder="Search events..." value="<%= keyword != null ? keyword : "" %>">
                    <button type="submit" class="btn btn-primary">Search</button>
                </form>
            </div>

            <% if (request.getAttribute("success") != null) { %>
            <div class="alert alert-success">
                <%= request.getAttribute("success") %>
            </div>
            <% } %>


            <% if (events != null && !events.isEmpty()) { %>
            <div class="event-grid">
                <% for (Event event : events) { %>
                <div class="event-card">
                    <!-- Event Image -->
                    <div class="event-image">
                        <% if (event.getEventImage() != null) { %>
                        <img src="${pageContext.request.contextPath}/event/image?id=<%= event.getEventId() %>"
                             alt="<%= event.getEventTitle() %>"
                             style="max-width: 100%; max-height: 200px; width: 100%; height: auto; object-fit: cover; border-radius: 8px; margin-bottom: 10px;">
                        <% } else { %>
                        <p style="text-align: center; color: #666; margin-bottom: 10px;">No image available</p>
                        <% } %>
                    </div>
                    <div class="event-card-header">
                        <h3 class="event-title"><%= event.getEventTitle() %></h3>
                        <span class="event-price">Rs <%= String.format("%.2f", event.getEventPrice()) %></span>
                    </div>
                    <div class="event-card-body">
                        <p class="event-date"><strong>Date:</strong> <%= event.getEventDate() %></p>
                        <p class="event-location"><strong>Location:</strong> <%= event.getEventLocation() %></p>
                        <p class="event-description"><%= event.getEventDescription().length() > 100 ? event.getEventDescription().substring(0, 100) + "..." : event.getEventDescription() %></p>
                    </div>
                    <div class="event-card-footer">
                        <a href="${pageContext.request.contextPath}/events/detail?id=<%= event.getEventId() %>" class="btn btn-secondary">View Details</a>
                        <a href="${pageContext.request.contextPath}/booking/form?eventId=<%= event.getEventId() %>" class="btn btn-primary">Book Now</a>
                    </div>
                </div>
                <% } %>
            </div>

            <% if (!isSearchResult && totalPages > 1) { %>
            <!-- Pagination -->
            <div class="pagination">
                <% if (currentPage > 1) { %>
                <a href="${pageContext.request.contextPath}/events?page=<%= currentPage - 1 %>" class="pagination-link">« Previous</a>
                <% } %>

                <% for (int i = 1; i <= totalPages; i++) { %>
                <a href="${pageContext.request.contextPath}/events?page=<%= i %>" class="pagination-link <%= (i == currentPage) ? "active" : "" %>"><%= i %></a>
                <% } %>

                <% if (currentPage < totalPages) { %>
                <a href="${pageContext.request.contextPath}/events?page=<%= currentPage + 1 %>" class="pagination-link">Next »</a>
                <% } %>
            </div>
            <% } %>
            <% } else { %>
            <div class="no-events">
                <% if (isSearchResult) { %>
                <p>No events found matching your search criteria.</p>
                <% } else { %>
                <p>No upcoming events at the moment. Check back later!</p>
                <% } %>
            </div>
            <% } %>
        </div>
    </section>
</main>

<!-- Include Footer -->
<jsp:include page="/includes/footer.jsp" />

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>