<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Blog, java.util.List" %>
<%
    List<Blog> blogs = (List<Blog>) request.getAttribute("blogs");
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
    <title><%= isSearchResult ? "Search Results" : "Blog" %> - Event Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <!-- Include Header -->
    <jsp:include page="/includes/header.jsp" />
    
    <main class="main-content">
        <section class="blog-section">
            <div class="container">
                <h1 class="section-title">
                    <% if (isSearchResult) { %>
                        Search Results for "<%= keyword %>"
                    <% } else { %>
                        Our Blog
                    <% } %>
                </h1>
                
                <!-- Search Form -->
                <div class="search-container">
                    <form action="${pageContext.request.contextPath}/blogs/search" method="get">
                        <input type="text" name="keyword" placeholder="Search blogs..." value="<%= keyword != null ? keyword : "" %>">
                        <button type="submit" class="btn btn-primary">Search</button>
                    </form>
                </div>
                
                <% if (blogs != null && !blogs.isEmpty()) { %>
                <div class="blog-list">
                    <% for (Blog blog : blogs) { %>
                    <div class="blog-card">
                        <div class="blog-card-content">
                            <h2 class="blog-title"><%= blog.getBlogTitle() %></h2>
                            <div class="blog-meta">
                                <span class="blog-date"><%= blog.getBlogPostDate() %></span>
                            </div>
                            <div class="blog-excerpt">
                                <p><%= blog.getBlogDescription().length() > 200 ? blog.getBlogDescription().substring(0, 200) + "..." : blog.getBlogDescription() %></p>
                            </div>
                            <div class="blog-actions">
                                <a href="${pageContext.request.contextPath}/blogs/detail?id=<%= blog.getBlogId() %>" class="btn btn-secondary">Read More</a>
                            </div>
                        </div>
                    </div>
                    <% } %>
                </div>
                
                <% if (!isSearchResult && totalPages > 1) { %>
                <!-- Pagination -->
                <div class="pagination">
                    <% if (currentPage > 1) { %>
                    <a href="${pageContext.request.contextPath}/blogs?page=<%= currentPage - 1 %>" class="pagination-link">&laquo; Previous</a>
                    <% } %>
                    
                    <% for (int i = 1; i <= totalPages; i++) { %>
                    <a href="${pageContext.request.contextPath}/blogs?page=<%= i %>" class="pagination-link <%= (i == currentPage) ? "active" : "" %>"><%= i %></a>
                    <% } %>
                    
                    <% if (currentPage < totalPages) { %>
                    <a href="${pageContext.request.contextPath}/blogs?page=<%= currentPage + 1 %>" class="pagination-link">Next &raquo;</a>
                    <% } %>
                </div>
                <% } %>
                <% } else { %>
                <div class="no-blogs">
                    <% if (isSearchResult) { %>
                    <p>No blogs found matching your search criteria.</p>
                    <% } else { %>
                    <p>No blog posts available at the moment. Check back later!</p>
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
