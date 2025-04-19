<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User, model.Contact, java.util.List" %>
<%
    // Check if user is logged in and is admin
    User user = (User) session.getAttribute("user");
    if (user == null || !user.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    
    List<Contact> contacts = (List<Contact>) request.getAttribute("contacts");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Messages - Event Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="admin-container">
        <div class="admin-sidebar">
            <div class="admin-sidebar-header">
                <h2>Admin Panel</h2>
            </div>
            <ul class="admin-menu">
                <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/events/list">Manage Events</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/users/list">Manage Users</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/feedback">View Feedback</a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/admin/contacts">View Messages</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/blogs/list">Manage Blogs</a></li>
                <li><a href="${pageContext.request.contextPath}/profile/edit">Edit Profile</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </ul>
        </div>
        
        <div class="admin-content">
            <div class="admin-header">
                <h1>Contact Messages</h1>
                <div class="admin-user">
                    <span>Welcome, <%= user.getUsername() %></span>
                </div>
            </div>
            
            <% if (request.getAttribute("success") != null) { %>
            <div class="alert alert-success">
                <%= request.getAttribute("success") %>
            </div>
            <% } %>
            
            <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">
                <%= request.getAttribute("error") %>
            </div>
            <% } %>
            
            <!-- Contact Messages List -->
            <div class="data-table">
                <% if (contacts != null && !contacts.isEmpty()) { %>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Subject</th>
                            <th>Message</th>
                            <th>Date</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Contact contact : contacts) { %>
                        <tr>
                            <td><%= contact.getContactId() %></td>
                            <td><%= contact.getContactName() %></td>
                            <td><%= contact.getContactEmail() %></td>
                            <td><%= contact.getContactSubject() %></td>
                            <td class="message-cell">
                                <div class="message-preview"><%= contact.getContactMessage().length() > 50 ? contact.getContactMessage().substring(0, 50) + "..." : contact.getContactMessage() %></div>
                                <div class="message-full hidden"><%= contact.getContactMessage() %></div>
                                <button class="btn-read-more">Read More</button>
                            </td>
                            <td><%= contact.getContactDate() %></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/contacts" method="post">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="contactId" value="<%= contact.getContactId() %>">
                                    <button type="submit" class="btn btn-small btn-danger" onclick="return confirm('Are you sure you want to delete this message?')">Delete</button>
                                </form>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
                <% } else { %>
                <p class="no-data">No contact messages found.</p>
                <% } %>
            </div>
        </div>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>
        // Add functionality for Read More buttons
        document.addEventListener('DOMContentLoaded', function() {
            const readMoreButtons = document.querySelectorAll('.btn-read-more');
            
            readMoreButtons.forEach(button => {
                button.addEventListener('click', function() {
                    const cell = this.parentElement;
                    const preview = cell.querySelector('.message-preview');
                    const full = cell.querySelector('.message-full');
                    
                    preview.classList.toggle('hidden');
                    full.classList.toggle('hidden');
                    
                    if (this.textContent === 'Read More') {
                        this.textContent = 'Show Less';
                    } else {
                        this.textContent = 'Read More';
                    }
                });
            });
        });
    </script>
</body>
</html>
