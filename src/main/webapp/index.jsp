<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Event, model.Blog, java.util.List" %>
<%
    // Retrieve featured events and latest blogs from request attributes
    List<Event> featuredEvents = (List<Event>) request.getAttribute("featuredEvents");
    List<Blog> latestBlogs = (List<Blog>) request.getAttribute("latestBlogs");
    
    // Get logged-in username from session
    String username = (String) session.getAttribute("username");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Page Metadata and CSS Link -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Event Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<!-- Include Header from external JSP -->
<jsp:include page="/includes/header.jsp" />

<main class="main-content">

    <!-- Hero Section (Intro Banner) -->
    <section class="hero-section">
        <div class="container">
            <div class="hero-content">
                <div class="hero-text">
                    <h1 class="hero-title">
                        <%-- Personalized greeting if logged in --%>
                        <% if (username != null) { %>
                        Welcome, <%= username %>!
                        <% } else { %>
                        Welcome to Event Management System
                        <% } %>
                    </h1>
                    <p class="hero-subtitle">Find and book amazing events or create your own!</p>

                    <%-- Buttons for browsing or registering based on login status --%>
                    <div class="hero-buttons <%= session.getAttribute("userId") != null ? "centered" : "" %>">
                        <a href="${pageContext.request.contextPath}/events" class="btn btn-primary">Browse Events</a>
                        <% if (session.getAttribute("userId") == null) { %>
                        <a href="${pageContext.request.contextPath}/register" class="btn btn-secondary">Register Now</a>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Featured Events Section -->
    <section class="featured-events">
        <div class="container">
            <h2 class="section-title">Featured Events</h2>

            <%-- Check if there are any featured events to display --%>
            <% if (featuredEvents != null && !featuredEvents.isEmpty()) { %>
            <div class="event-grid">
                <%-- Loop through each event and display its information --%>
                <% for (Event event : featuredEvents) { %>
                <div class="event-card">
                    <div class="event-card-header">
                        <h3 class="event-title"><%= event.getEventTitle() %></h3>
                        <span class="event-price">Rs <%= String.format("%.2f", event.getEventPrice()) %></span>
                    </div>

                    <%-- Event Image Section --%>
                    <div class="event-image">
                        <% if (event.getEventImage() != null) { %>
                        <img src="${pageContext.request.contextPath}/event/image?id=<%= event.getEventId() %>"
                             alt="<%= event.getEventTitle() %>"
                             style="max-width: 100%; max-height: 200px; width: 100%; height: auto; object-fit: cover; border-radius: 8px; margin-bottom: 10px;">
                        <% } else { %>
                        <p style="text-align: center; color: #666; margin-bottom: 10px;">No image available</p>
                        <% } %>
                    </div>

                    <%-- Event Details --%>
                    <div class="event-card-body">
                        <p class="event-date"><strong>Date:</strong> <%= event.getEventDate() %></p>
                        <p class="event-location"><strong>Location:</strong> <%= event.getEventLocation() %></p>
                        <p class="event-description">
                            <%= event.getEventDescription().length() > 100 ? event.getEventDescription().substring(0, 100) + "..." : event.getEventDescription() %>
                        </p>
                    </div>

                    <%-- Event Actions --%>
                    <div class="event-card-footer">
                        <a href="${pageContext.request.contextPath}/events/detail?id=<%= event.getEventId() %>" class="btn btn-secondary">View Details</a>
                        <a href="${pageContext.request.contextPath}/booking/form?eventId=<%= event.getEventId() %>" class="btn btn-primary">Book Now</a>
                    </div>
                </div>
                <% } %>
            </div>

            <%-- Link to view all events --%>
            <div style="text-align: center; margin-top: 30px;">
                <a href="${pageContext.request.contextPath}/events" class="btn btn-outline">View All Events</a>
            </div>

            <% } else { %>
            <%-- Message if no events available --%>
            <div class="no-events">
                <p>No featured events at the moment. Check back later!</p>
            </div>
            <% } %>
        </div>
    </section>

    <!-- Latest Blog Posts Section -->
    <section class="latest-blogs">
        <div class="container">
            <h2 class="section-title">Latest Blog Posts</h2>

            <%-- Inline CSS for blog images --%>
            <style>
                .blog-image {
                    width: 100%;
                    height: 200px;
                    object-fit: cover;
                    display: block;
                }
            </style>

            <%-- Check if there are blog posts to display --%>
            <% if (latestBlogs != null && !latestBlogs.isEmpty()) { %>
            <div class="blog-grid">
                <%-- Loop through blogs --%>
                <% for (Blog blog : latestBlogs) { %>
                <div class="blog-card">
                    <%-- Blog Image --%>
                    <% if (blog.getBlogImage() != null) { %>
                    <div class="blog-card-image">
                        <img src="${pageContext.request.contextPath}/image?blogId=<%= blog.getBlogId() %>"
                             alt="<%= blog.getBlogTitle() %>" class="blog-image">
                    </div>
                    <% } %>

                    <%-- Blog Content --%>
                    <div class="blog-card-content">
                        <h2 class="blog-title"><%= blog.getBlogTitle() %></h2>
                        <div class="blog-meta">
                            <span class="blog-date"><%= blog.getBlogPostDate() %></span>
                        </div>
                        <div class="blog-excerpt">
                            <p><%= blog.getBlogDescription().length() > 150 ? blog.getBlogDescription().substring(0, 150) + "..." : blog.getBlogDescription() %></p>
                        </div>
                        <div class="blog-actions">
                            <a href="${pageContext.request.contextPath}/blogs/detail?id=<%= blog.getBlogId() %>" class="btn btn-secondary">Read More</a>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>

            <%-- Link to more blog articles --%>
            <div style="text-align: center; margin-top: 30px;">
                <a href="${pageContext.request.contextPath}/blogs" class="btn btn-outline">Read More Articles</a>
            </div>
            <% } else { %>
            <%-- Message if no blogs --%>
            <div class="no-blogs">
                <p>No blog posts available at the moment. Check back later!</p>
            </div>
            <% } %>
        </div>
    </section>

</main>

<!-- Include Footer from external JSP -->
<jsp:include page="/includes/footer.jsp" />

<!-- Link to JavaScript file -->
<script src="${pageContext.request.contextPath}/js/main.js"></script>

</body>
</html>
