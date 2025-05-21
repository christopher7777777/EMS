<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User, java.util.Base64" %>
<%
    User currentUser = (User) session.getAttribute("user");
    boolean isLoggedIn = currentUser != null;
    boolean isAdmin = isLoggedIn && currentUser.isAdmin();
%>
<header class="header">
    <div class="container">
        <nav class="navbar <%= request.getAttribute("mobileMenuOpen") != null ? "mobile-menu-open" : "mobile-menu-closed" %>">
            <a href="${pageContext.request.contextPath}/" class="brand">Event Manager</a>

            <button class="mobile-menu-btn" id="mobileMenuBtn">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <line x1="3" y1="12" x2="21" y2="12"></line>
                    <line x1="3" y1="6" x2="21" y2="6"></line>
                    <line x1="3" y1="18" x2="21" y2="18"></line>
                </svg>
            </button>

            <ul class="nav-menu">
                <li><a href="${pageContext.request.contextPath}/" class="nav-link">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/events" class="nav-link">Events</a></li>
                <li><a href="${pageContext.request.contextPath}/blogs" class="nav-link">Blog</a></li>
                <li><a href="${pageContext.request.contextPath}/about" class="nav-link">About Us</a></li>
                <li><a href="${pageContext.request.contextPath}/contact" class="nav-link">Contact</a></li>

                <% if (isLoggedIn) { %>
                <li class="user-dropdown">
                    <div class="user-dropdown-toggle">
                        <% if (currentUser.getProfilePicture() != null && currentUser.getProfilePicture().length > 0) { %>
                        <img src="data:image/jpeg;base64,<%= Base64.getEncoder().encodeToString(currentUser.getProfilePicture()) %>" alt="<%= currentUser.getUsername() %>" class="user-avatar">
                        <% } else { %>
                        <div class="user-avatar-placeholder"><%= currentUser.getUsername().substring(0, 1).toUpperCase() %></div>
                        <% } %>
                        <%= currentUser.getUsername() %>
                    </div>
                    <ul class="user-menu">
                        <% if (isAdmin) { %>
                        <li><a href="${pageContext.request.contextPath}/admin/dashboard" class="user-menu-link">Admin Dashboard</a></li>
                        <% } %>
                        <li><a href="${pageContext.request.contextPath}/profile/edit" class="user-menu-link">My Profile</a></li>
                        <li><a href="${pageContext.request.contextPath}/booking/list" class="user-menu-link">My Booking</a></li>
                        <li><a href="${pageContext.request.contextPath}/logout" class="user-menu-link">Logout</a></li>
                    </ul>
                </li>
                <% } else { %>
                <li><a href="${pageContext.request.contextPath}/login" class="nav-link">Login</a></li>
                <li><a href="${pageContext.request.contextPath}/register" class="nav-link">Register</a></li>
                <% } %>
            </ul>
        </nav>
    </div>
</header>
