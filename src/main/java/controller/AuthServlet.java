package controller;

import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "AuthServlet", urlPatterns = {"/login", "/register"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 5,   // 5 MB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class AuthServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/login":
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                break;
            case "/register":
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/login":
                authenticateUser(request, response);
                break;
            case "/register":
                registerUser(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/");
                break;
        }
    }

    /**
     * Authenticate a user (login)
     */
    private void authenticateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        // Validate input
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and password are required.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // Authenticate user
        User user = userDAO.authenticateUser(username, password);

        if (user != null) {
            // User authenticated successfully
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(5 * 60);
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("role", user.getRole().toString());

            // Set or remove "remember me" cookie
            Cookie usernameCookie = new Cookie("username", "");
            usernameCookie.setPath(request.getContextPath());

            if ("on".equals(rememberMe)) {
                usernameCookie.setValue(username);
                usernameCookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
            } else {
                usernameCookie.setMaxAge(0); // Delete cookie
            }

            response.addCookie(usernameCookie);

            // Redirect based on role
            if (user.isAdmin()) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
        } else {
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    /**
     * Register a new user
     */
    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String roleStr = request.getParameter("role");

        // Get profile picture from multipart form
        Part filePart = request.getPart("profilePicture");
        InputStream fileContent = null;
        byte[] profilePicture = null;

        if (filePart != null && filePart.getSize() > 0) {
            fileContent = filePart.getInputStream();
            profilePicture = new byte[(int) filePart.getSize()];
            fileContent.read(profilePicture);
        }

        // Validate input
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                confirmPassword == null || confirmPassword.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                roleStr == null || roleStr.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Validate password requirements
        String passwordRegex = "^(?=.*[A-Z])(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{6,}$";
        if (!password.matches(passwordRegex)) {
            request.setAttribute("error", "Password must be at least 6 characters long, contain at least one capital letter, and one symbol (!@#$%^&*).");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Check if username already exists
        if (userDAO.usernameExists(username)) {
            request.setAttribute("error", "Username already exists.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Check if email already exists
        if (userDAO.emailExists(email)) {
            request.setAttribute("error", "Email already exists.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Create user
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // Will be hashed in DAO
        user.setEmail(email);

        try {
            user.setRole(User.Role.valueOf(roleStr.toUpperCase()));
        } catch (IllegalArgumentException e) {
            user.setRole(User.Role.CUSTOMER);
        }

        user.setProfilePicture(profilePicture);
        user.setActive(true);

        // Save user to database
        int userId = userDAO.addUser(user);

        if (userId > 0) {
            request.setAttribute("success", "Registration successful! Please log in.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}