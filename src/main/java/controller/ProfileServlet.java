package controller;

import dao.UserDAO;
import model.User;
import utils.PasswordHash;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

/**
 * Servlet for handling user profile management
 */
@WebServlet("/profile/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 5,   // 5 MB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class ProfileServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if user is logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getPathInfo();
        if (action == null) {
            action = "/edit";
        }

        switch (action) {
            case "/edit":
                showEditProfileForm(request, response);
                break;
            case "/changePassword":
                showChangePasswordForm(request, response);
                break;
            case "/delete":
                showDeleteProfileForm(request, response);
                break;
            default:
                showEditProfileForm(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if user is logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getPathInfo();
        if (action == null) {
            action = "/update";
        }

        switch (action) {
            case "/update":
                updateProfile(request, response);
                break;
            case "/changePassword":
                changePassword(request, response);
                break;
            case "/delete":
                deleteProfile(request, response);
                break;
            default:
                showEditProfileForm(request, response);
                break;
        }
    }

    /**
     * Show the edit profile form
     */
    private void showEditProfileForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        // Check if user is logged in
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Forward to appropriate profile edit page based on role
        if (user.isAdmin()) {
            request.getRequestDispatcher("/admin/edit-profile.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/customer/edit-profile.jsp").forward(request, response);
        }
    }

    /**
     * Show the change password form
     */
    private void showChangePasswordForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        // Check if user is logged in
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Forward to appropriate change password page based on role
        if (user.isAdmin()) {
            request.getRequestDispatcher("/admin/edit-profile.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/customer/edit-profile.jsp").forward(request, response);
        }
    }

    /**
     * Show the delete profile form
     */
    private void showDeleteProfileForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        // Check if user is logged in
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Forward to appropriate delete profile page based on role
        if (user.isAdmin()) {
            request.getRequestDispatcher("/admin/delete-profile.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/customer/delete-profile.jsp").forward(request, response);
        }
    }

    /**
     * Update user profile
     */
    private void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        String username = request.getParameter("username");
        String email = request.getParameter("email");

        // Validate input
        if (username == null || username.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {

            request.setAttribute("error", "Username and email are required.");
            showEditProfileForm(request, response);
            return;
        }

        // Check if username already exists (for another user)
        User existingUser = userDAO.getUserByUsername(username);
        if (existingUser != null && existingUser.getUserId() != currentUser.getUserId()) {
            request.setAttribute("error", "Username already exists.");
            showEditProfileForm(request, response);
            return;
        }

        // Get profile picture from multipart form
        Part filePart = request.getPart("profilePicture");
        InputStream fileContent = null;
        byte[] profilePicture = currentUser.getProfilePicture(); // Keep existing by default

        if (filePart != null && filePart.getSize() > 0) {
            fileContent = filePart.getInputStream();
            profilePicture = new byte[(int) filePart.getSize()];
            fileContent.read(profilePicture);
        }

        // Update user
        currentUser.setUsername(username);
        currentUser.setEmail(email);
        currentUser.setProfilePicture(profilePicture);

        boolean success = userDAO.updateUser(currentUser);

        if (success) {
            // Update session attributes
            session.setAttribute("user", currentUser);
            session.setAttribute("username", currentUser.getUsername());

            request.setAttribute("success", "Profile updated successfully.");
        } else {
            request.setAttribute("error", "Failed to update profile.");
        }

        showEditProfileForm(request, response);
    }

    /**
     * Change user password
     */
    private void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate input
        if (currentPassword == null || currentPassword.trim().isEmpty() ||
                newPassword == null || newPassword.trim().isEmpty() ||
                confirmPassword == null || confirmPassword.trim().isEmpty()) {

            request.setAttribute("error", "All fields are required.");
            showChangePasswordForm(request, response);
            return;
        }

        // Verify current password
        if (!PasswordHash.verifyPassword(currentPassword, currentUser.getPassword())) {
            request.setAttribute("error", "Current password is incorrect.");
            showChangePasswordForm(request, response);
            return;
        }

        // Verify new password confirmation
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New passwords do not match.");
            showChangePasswordForm(request, response);
            return;
        }

        // Update password
        currentUser.setPassword(newPassword); // Will be hashed in DAO
        boolean success = userDAO.updateUser(currentUser);

        if (success) {
            // Update session
            session.setAttribute("user", currentUser);

            request.setAttribute("success", "Password changed successfully.");
        } else {
            request.setAttribute("error", "Failed to change password.");
        }

        showChangePasswordForm(request, response);
    }

    /**
     * Delete user profile
     */
    private void deleteProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        String password = request.getParameter("password");

        // Validate input
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Password is required to delete profile.");
            showDeleteProfileForm(request, response);
            return;
        }

        // Verify password
        if (!PasswordHash.verifyPassword(password, currentUser.getPassword())) {
            request.setAttribute("error", "Incorrect password.");
            showDeleteProfileForm(request, response);
            return;
        }

        // Delete user
        boolean success = userDAO.deleteUser(currentUser.getUserId());

        if (success) {
            // Invalidate session
            session.invalidate();
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            request.setAttribute("success", "Profile deleted successfully.");
        } else {
            request.setAttribute("error", "Failed to delete profile.");
            showDeleteProfileForm(request, response);
        }
    }
}