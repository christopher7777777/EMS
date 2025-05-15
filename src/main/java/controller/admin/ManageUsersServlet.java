package controller.admin;

import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for managing users (CRUD operations)
 */
@WebServlet("/admin/users/*")
public class ManageUsersServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if user is logged in and is admin
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/access-denied.jsp");
            return;
        }

        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        switch (action) {
            case "/list":
                listUsers(request, response);
                break;
            case "/block":
            case "/unblock":
                toggleUserStatus(request, response);
                break;
            case "/delete":
                deleteUser(request, response);
                break;
            default:
                listUsers(request, response);
                break;
        }
    }

    /**
     * List all users
     */
    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userDAO.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/admin/manage-users.jsp").forward(request, response);
    }

    /**
     * Toggle user active status (block/unblock)
     */
    private void toggleUserStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        int userId = Integer.parseInt(request.getParameter("id"));
        User currentUser = (User) request.getSession().getAttribute("user");

        // Prevent admin from blocking themselves
        if (userId == currentUser.getUserId()) {
            request.setAttribute("error", "You cannot block/unblock yourself.");
            listUsers(request, response);
            return;
        }

        // Fetch the target user to check their role
        User targetUser = userDAO.getUserById(userId);
        if (targetUser == null) {
            request.setAttribute("error", "User not found.");
            listUsers(request, response);
            return;
        }

        // Check if the target user is a customer
        if (targetUser.getRole() != User.Role.CUSTOMER) {
            request.setAttribute("error", "You can only block/unblock users with the customer role.");
            listUsers(request, response);
            return;
        }

        boolean isActive = action.equals("/unblock");
        boolean success = userDAO.toggleUserActiveStatus(userId, isActive);

        if (success) {
            request.setAttribute("success", "User " + (isActive ? "unblocked" : "blocked") + " successfully.");
        } else {
            request.setAttribute("error", "Failed to " + (isActive ? "unblock" : "block") + " user.");
        }

        listUsers(request, response);
    }

    /**
     * Delete a user
     */
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        User currentUser = (User) request.getSession().getAttribute("user");

        // Prevent admin from deleting themselves
        if (userId == currentUser.getUserId()) {
            request.setAttribute("error", "You cannot delete yourself.");
            listUsers(request, response);
            return;
        }

        // Fetch the target user to check their role
        User targetUser = userDAO.getUserById(userId);
        if (targetUser == null) {
            request.setAttribute("error", "User not found.");
            listUsers(request, response);
            return;
        }

        // Check if the target user is a customer
        if (targetUser.getRole() != User.Role.CUSTOMER) {
            request.setAttribute("error", "You can only delete users with the customer role.");
            listUsers(request, response);
            return;
        }

        boolean success = userDAO.deleteUser(userId);

        if (success) {
            request.setAttribute("success", "User deleted successfully.");
        } else {
            request.setAttribute("error", "Failed to delete user.");
        }

        listUsers(request, response);
    }
}