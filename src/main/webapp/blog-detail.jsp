
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Blog, model.Event" %>
<%
    Blog blog = (Blog) request.getAttribute("blog");
    Event event = (Event) request.getAttribute("event");

    if (blog == null) {
        response.sendRedirect(request.getContextPath() + "/blogs");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= blog.getBlogTitle() %> - Event Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .blog-image {
            width: 100%; /* Maintains full width of the container */
            height: 200px; /* Fixed height to match blog list */
            object-fit: cover; /* Ensures image fits without distortion */
            display: block; /* Removes extra spacing */
            margin-bottom: 20px; /* Adds spacing below the image */
        }
    </style>
</head>
<body>
<!-- Include Header -->
<jsp:include page="/includes/header.jsp" />

<main class="main-content">
    <article class="blog-detail">
        <div class="container">
            <div class="blog-header">
                <h1 class="blog-title"><%= blog.getBlogTitle() %></h1>
                <div class="blog-meta">
                    <span class="blog-date"><%= blog.getBlogPostDate() %></span>
                </div>
                <% if (blog.getBlogImage() != null) { %>
                <div class="blog-image-container">
                    <img src="${pageContext.request.contextPath}/image?blogId=<%= blog.getBlogId() %>" alt="<%= blog.getBlogTitle() %>" class="blog-image">
                </div>
                <% } %>
            </div>

            <div class="blog-content">
                <p><%= blog.getBlogDescription().replace("\n", "<br>") %></p>
            </div>

            <% if (event != null) { %>
            <div class="related-event">
                <h3>Related Event</h3>
                <div class="event-card">
                    <div class="event-card-header">
                        <h3 class="event-title"><%= event.getEventTitle() %></h3>
                        <span class="event-price">Rs <%= String.format("%.2f", event.getEventPrice()) %></span>
                    </div>
                    <div class="event-card-body">
                        <p class="event-date"><strong>Date:</strong> <%= event.getEventDate() %></p>
                        <p class="event-location"><strong>Location:</strong> <%= event.getEventLocation() %></p>
                    </div>
                    <div class="event-card-footer">
                        <a href="${pageContext.request.contextPath}/events/detail?id=<%= event.getEventId() %>" class="btn btn-secondary">View Event</a>
                    </div>
                </div>
            </div>
            <% } %>

            <div class="blog-navigation">
                <a href="${pageContext.request.contextPath}/blogs" class="btn btn-outline">Back to Blog List</a>
            </div>
        </div>
    </article>
</main>

<!-- Include Footer -->
<jsp:include page="/includes/footer.jsp" />

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
