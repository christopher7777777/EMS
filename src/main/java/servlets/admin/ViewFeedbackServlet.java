package servlets.admin;

import dao.EventDAO;
import dao.FeedbackDAO;
import model.Event;
import model.Feedback;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet for viewing feedback as admin
 */
@WebServlet("/admin/feedback")
public class ViewFeedbackServlet extends HttpServlet {
    
    private FeedbackDAO feedbackDAO;
    private EventDAO eventDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        feedbackDAO = new FeedbackDAO();
        eventDAO = new EventDAO();
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
        
        // Handle filtering by event if provided
        String eventIdParam = request.getParameter("eventId");
        List<Feedback> feedbackList;
        
        if (eventIdParam != null && !eventIdParam.isEmpty()) {
            int eventId = Integer.parseInt(eventIdParam);
            feedbackList = feedbackDAO.getFeedbackByEventId(eventId);
        } else {
            feedbackList = feedbackDAO.getAllFeedback();
        }
        
        // Get event details for each feedback
        Map<Integer, Event> eventMap = new HashMap<>();
        for (Feedback feedback : feedbackList) {
            int eventId = feedback.getEventId();
            if (!eventMap.containsKey(eventId)) {
                Event event = eventDAO.getEventById(eventId);
                if (event != null) {
                    eventMap.put(eventId, event);
                }
            }
        }
        
        // Get all events for filter dropdown
        List<Event> allEvents = eventDAO.getAllEvents();
        
        request.setAttribute("feedbackList", feedbackList);
        request.setAttribute("eventMap", eventMap);
        request.setAttribute("allEvents", allEvents);
        
        request.getRequestDispatcher("/admin/view-feedback.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle delete feedback if needed
        String action = request.getParameter("action");
        
        if ("delete".equals(action)) {
            int feedbackId = Integer.parseInt(request.getParameter("feedbackId"));
            boolean success = feedbackDAO.deleteFeedback(feedbackId);
            
            if (success) {
                request.setAttribute("success", "Feedback deleted successfully.");
            } else {
                request.setAttribute("error", "Failed to delete feedback.");
            }
            
            doGet(request, response);
        }
    }
}
