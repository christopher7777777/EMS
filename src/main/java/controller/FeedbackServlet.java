package controller;

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
import java.sql.Timestamp;

/**
 * Servlet for handling feedback submissions
 */
@WebServlet("/feedback/*")
public class FeedbackServlet extends HttpServlet {

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
        String action = request.getPathInfo();

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/events");
            return;
        }

        if (action.equals("/form")) {
            showFeedbackForm(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/events");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/events");
            return;
        }

        if (action.equals("/submit")) {
            submitFeedback(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/events");
        }
    }

    /**
     * Show the feedback form for an event
     */
    private void showFeedbackForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if user is logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String eventIdStr = request.getParameter("eventId");

        if (eventIdStr == null || eventIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/events");
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdStr);
            Event event = eventDAO.getEventById(eventId);

            if (event == null) {
                response.sendRedirect(request.getContextPath() + "/events");
                return;
            }

            request.setAttribute("event", event);
            request.getRequestDispatcher("/customer/feedback-form.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/events");
        }
    }

    /**
     * Submit feedback for an event
     */
    private void submitFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if user is logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        String eventIdStr = request.getParameter("eventId");
        String ratingStr = request.getParameter("rating");
        String comment = request.getParameter("comment");

        // Validate input
        if (eventIdStr == null || eventIdStr.trim().isEmpty() ||
                ratingStr == null || ratingStr.trim().isEmpty() ||
                comment == null || comment.trim().isEmpty()) {

            request.setAttribute("error", "All fields are required.");
            showFeedbackForm(request, response);
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdStr);
            int rating = Integer.parseInt(ratingStr);

            // Validate rating range
            if (rating < 1 || rating > 5) {
                request.setAttribute("error", "Rating must be between 1 and 5.");
                showFeedbackForm(request, response);
                return;
            }

            Event event = eventDAO.getEventById(eventId);

            if (event == null) {
                response.sendRedirect(request.getContextPath() + "/events");
                return;
            }

            // Create feedback
            Feedback feedback = new Feedback();
            feedback.setFeedbackEventId(eventId);
            feedback.setFeedbackRating(rating);
            feedback.setFeedbackComment(comment);
            feedback.setFeedbackDate(new Timestamp(System.currentTimeMillis()));
            feedback.setEventId(eventId);

            int feedbackId = feedbackDAO.addFeedback(feedback);

            if (feedbackId > 0) {
                session.setAttribute("success", "Your feedback has been submitted successfully.");
            } else {
                session.setAttribute("error", "Failed to submit feedback.");
            }

            // Redirect back to the feedback form
            response.sendRedirect(request.getContextPath() + "/feedback/form?eventId=" + eventId);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid input format.");
            showFeedbackForm(request, response);
        }
    }
}