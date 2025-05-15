package controller.admin;

import dao.*;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet for the admin dashboard
 * Displays summary statistics for the admin
 */
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    
    private UserDAO userDAO;
    private EventDAO eventDAO;
    private BookingDAO bookingDAO;
    private ContactDAO contactDAO;
    private FeedbackDAO feedbackDAO;
    private BlogDAO blogDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
        eventDAO = new EventDAO();
        bookingDAO = new BookingDAO();
        contactDAO = new ContactDAO();
        feedbackDAO = new FeedbackDAO();
        blogDAO = new BlogDAO();
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
        
        // Get statistics for dashboard
        int totalUsers = userDAO.countTotalUsers();
        int totalCustomers = userDAO.countUsersByRole(User.Role.CUSTOMER);
        int totalEvents = eventDAO.countTotalEvents();
        int totalBookings = bookingDAO.countTotalBookings();
        int totalContacts = contactDAO.countTotalContacts();
        int totalFeedback = feedbackDAO.countTotalFeedback();
        int totalBlogs = blogDAO.countTotalBlogs();
        
        // Set attributes for the dashboard
        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("totalCustomers", totalCustomers);
        request.setAttribute("totalEvents", totalEvents);
        request.setAttribute("totalBookings", totalBookings);
        request.setAttribute("totalContacts", totalContacts);
        request.setAttribute("totalFeedback", totalFeedback);
        request.setAttribute("totalBlogs", totalBlogs);
        
        // Forward to dashboard JSP
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}
