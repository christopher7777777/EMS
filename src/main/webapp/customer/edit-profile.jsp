<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User, java.util.Base64" %>
<%
    // Check if user is logged in
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    // Check if user is customer (admin has a different profile page)
    if (!user.isCustomer()) {
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - Event Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<!-- Include Header -->
<jsp:include page="/includes/header.jsp" />

<main class="main-content">
    <section class="profile-section">
        <div class="container">
            <h1 class="section-title">My Profile</h1>

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

            <div class="profile-container">
                <div class="profile-card">
                    <div class="profile-image">
                        <% if (user.getProfilePicture() != null && user.getProfilePicture().length > 0) { %>
                        <img src="data:image/jpeg;base64,<%= Base64.getEncoder().encodeToString(user.getProfilePicture()) %>" alt="<%= user.getUsername() %>">
                        <% } else { %>
                        <div class="profile-placeholder"><%= user.getUsername().substring(0, 1).toUpperCase() %></div>
                        <% } %>
                    </div>

                    <div class="profile-info">
                        <h2><%= user.getUsername() %></h2>
                        <p><%= user.getEmail() %></p>
                        <p>Role: <%= user.getRole() %></p>
                    </div>
                </div>

                <div class="profile-tabs">
                    <button class="tab-button active" data-target="edit-profile">Edit Profile</button>
                    <button class="tab-button" data-target="change-password">Change Password</button>
                    <button class="tab-button" data-target="delete-profile">Delete Profile</button>
                </div>

                <div class="tab-content" id="edit-profile">
                    <h3>Edit Profile Information</h3>
                    <form action="${pageContext.request.contextPath}/profile/update" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input type="text" id="username" name="username" value="<%= user.getUsername() %>" required>
                        </div>

                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required>
                        </div>

                        <div class="form-group">
                            <label for="profilePicture">Profile Picture</label>
                            <input type="file" id="profilePicture" name="profilePicture" accept="image/*">
                            <small>Leave empty to keep current picture</small>
                        </div>

                        <div class="form-buttons">
                            <button type="submit" class="btn btn-primary">Update Profile</button>
                        </div>
                    </form>
                </div>

                <div class="tab-content hidden" id="change-password">
                    <h3>Change Password</h3>
                    <form action="${pageContext.request.contextPath}/profile/changePassword" method="post">
                        <div class="form-group">
                            <label for="currentPassword">Current Password</label>
                            <input type="password" id="currentPassword" name="currentPassword" required>
                        </div>

                        <div class="form-group">
                            <label for="newPassword">New Password</label>
                            <input type="password" id="newPassword" name="newPassword" required>
                        </div>

                        <div class="form-group">
                            <label for="confirmPassword">Confirm New Password</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" required>
                        </div>

                        <div class="form-buttons">
                            <button type="submit" class="btn btn-primary">Change Password</button>
                        </div>
                    </form>
                </div>

                <div class="tab-content hidden" id="delete-profile">
                    <h3>Delete Profile</h3>
                    <div class=>
                        Warning: This action cannot be undone. All your data will be permanently deleted.
                    </div>
                    <form id="deleteProfileForm" action="${pageContext.request.contextPath}/profile/delete" method="post" onsubmit="return confirmDelete()">
                        <div class="form-group">
                            <label for="password">Enter Password to Confirm</label>
                            <input type="password" id="password" name="password" required>
                        </div>

                        <div class="form-buttons">
                            <button type="submit" class="btn btn-danger">Delete Profile</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</main>

<!-- Include Footer -->
<jsp:include page="/includes/footer.jsp" />

<script>
    // Tab functionality
    document.addEventListener('DOMContentLoaded', function() {
        const tabButtons = document.querySelectorAll('.tab-button');
        const tabContents = document.querySelectorAll('.tab-content');

        tabButtons.forEach(button => {
            button.addEventListener('click', function() {
                // Remove active class from all buttons
                tabButtons.forEach(btn => btn.classList.remove('active'));

                // Add active class to clicked button
                this.classList.add('active');

                // Hide all tab contents
                tabContents.forEach(content => content.classList.add('hidden'));

                // Show the target tab content
                const targetId = this.getAttribute('data-target');
                document.getElementById(targetId).classList.remove('hidden');
            });
        });
    });

    // Confirmation dialog for profile deletion
    function confirmDelete() {
        return confirm("Do you really want to delete your profile? This action cannot be undone.");
    }
</script>
</body>
</html>