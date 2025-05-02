<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Event, model.Blog, java.util.List" %>
<%
    List<Event> featuredEvents = (List<Event>) request.getAttribute("featuredEvents");
    List<Blog> latestBlogs = (List<Blog>) request.getAttribute("latestBlogs");
    String username = (String) session.getAttribute("username");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Event Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
<!-- Include Header -->
<jsp:include page="/includes/header.jsp" />

<main class="main-content">
    <!-- Hero Section -->
    <section class="hero-section">
        <div class="container">
            <div class="hero-content">
                <div class="hero-text">
                    <h1 class="hero-title">
                        <% if (username != null) { %>
                        Welcome, <%= username %>!
                        <% } else { %>
                        Welcome to Event Management System
                        <% } %>
                    </h1>
                    <p class="hero-subtitle">Find and book amazing events or create your own!</p>
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

            <% if (featuredEvents != null && !featuredEvents.isEmpty()) { %>
            <div class="event-grid">
                <% for (Event event : featuredEvents) { %>
                <div class="event-card">
                    <div class="event-card-header">
                        <h3 class="event-title"><%= event.getEventTitle() %></h3>
                        <span class="event-price">Rs <%= String.format("%.2f", event.getEventPrice()) %></span>
                    </div>
                    <div class="event-image">
                        <% if (event.getEventImage() != null) { %>
                        <img src="${pageContext.request.contextPath}/event/image?id=<%= event.getEventId() %>"
                             alt="<%= event.getEventTitle() %>"
                             style="max-width: 100%; max-height: 200px; width: 100%; height: auto; object-fit: cover; border-radius: 8px; margin-bottom: 10px;">
                        <% } else { %>
                        <p style="text-align: center; color: #666; margin-bottom: 10px;">No image available</p>
                        <% } %>
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
            <div style="text-align: center; margin-top: 30px;">
                <a href="${pageContext.request.contextPath}/events" class="btn btn-outline">View All Events</a>
            </div>
            <% } else { %>
            <div class="no-events">
                <p>No featured events at the moment. Check back later!</p>
            </div>
            <% } %>
        </div>
    </section>

    <!-- Latest Blog Section -->

    <!-- Latest Blog Section -->
    <section class="latest-blogs">
        <div class="container">
            <h2 class="section-title">Latest Blog Posts</h2>

            <style>
                .blog-image {
                    width: 100%; /* Maintains full width of the container */
                    height: 200px; /* Fixed height to match previous pages */
                    object-fit: cover; /* Ensures image fits without distortion */
                    display: block; /* Removes extra spacing */
                }
            </style>

            <% if (latestBlogs != null && !latestBlogs.isEmpty()) { %>
            <div class="blog-grid">
                <% for (Blog blog : latestBlogs) { %>
                <div class="blog-card">
                    <% if (blog.getBlogImage() != null) { %>
                    <div class="blog-card-image">
                        <img src="${pageContext.request.contextPath}/image?blogId=<%= blog.getBlogId() %>" alt="<%= blog.getBlogTitle() %>" class="blog-image">
                    </div>
                    <% } %>
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
            <div style="text-align: center; margin-top: 30px;">
                <a href="${pageContext.request.contextPath}/blogs" class="btn btn-outline">Read More Articles</a>
            </div>
            <% } else { %>
            <div class="no-blogs">
                <p>No blog posts available at the moment. Check back later!</p>
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