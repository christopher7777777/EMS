package controller;

import dao.EventDAO;
import model.Event;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling events (listing and details)
 */
@WebServlet("/events/*")
public class EventsServlet extends HttpServlet {
    
    private EventDAO eventDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        eventDAO = new EventDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        
        if (action == null || action.equals("/")) {
            listEvents(request, response);
        } else if (action.equals("/detail")) {
            showEventDetails(request, response);
        } else if (action.equals("/search")) {
            searchEvents(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/events");
        }
    }
    
    /**
     * List all events with pagination
     */
    private void listEvents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get page parameters
        int page = 1;
        int recordsPerPage = 6;
        
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        
        // Calculate offset
        int offset = (page - 1) * recordsPerPage;
        
        // Get events for current page
        List<Event> events = eventDAO.getPaginatedEvents(offset, recordsPerPage);
        
        // Count total events for pagination
        int totalEvents = eventDAO.countTotalEvents();
        int totalPages = (int) Math.ceil(totalEvents * 1.0 / recordsPerPage);
        
        request.setAttribute("events", events);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        
        request.getRequestDispatcher("/customer/event-listing.jsp").forward(request, response);
    }
    
    /**
     * Show details for a specific event
     */
    private void showEventDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventIdStr = request.getParameter("id");
        
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
            request.getRequestDispatcher("/customer/event-detail.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/events");
        }
    }
    
    /**
     * Search for events
     */
    private void searchEvents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        
        if (keyword == null || keyword.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/events");
            return;
        }
        
        List<Event> searchResults = eventDAO.searchEvents(keyword);
        
        request.setAttribute("events", searchResults);
        request.setAttribute("keyword", keyword);
        request.setAttribute("isSearchResult", true);
        
        request.getRequestDispatcher("/customer/event-listing.jsp").forward(request, response);
    }
}
