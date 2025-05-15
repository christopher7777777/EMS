package controller.admin;

import dao.EventDAO;
import model.Event;
import model.User;

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
import java.sql.Date;
import java.util.List;

/**
 * Servlet for managing events (CRUD operations)
 */
@WebServlet("/admin/events/*")
@MultipartConfig(maxFileSize = 16777215) // 16MB max file size (suitable for MEDIUMBLOB)
public class ManageEventsServlet extends HttpServlet {

    private EventDAO eventDAO;

    @Override
    public void init() throws ServletException {
        super.init();
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

        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        switch (action) {
            case "/list":
                listEvents(request, response);
                break;
            case "/add":
                showAddForm(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/delete":
                deleteEvent(request, response);
                break;
            default:
                listEvents(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            case "/add":
                addEvent(request, response);
                break;
            case "/update":
                updateEvent(request, response);
                break;
            default:
                listEvents(request, response);
                break;
        }
    }

    /**
     * List all events
     */
    private void listEvents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Event> events = eventDAO.getAllEvents();
        request.setAttribute("events", events);
        request.getRequestDispatcher("/admin/manage-events.jsp").forward(request, response);
    }

    /**
     * Show the add event form
     */
    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("action", "add");
        request.getRequestDispatcher("/admin/manage-events.jsp").forward(request, response);
    }

    /**
     * Show the edit event form
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("id"));
        Event event = eventDAO.getEventById(eventId);
        request.setAttribute("event", event);
        request.getRequestDispatcher("/admin/manage-events.jsp").forward(request, response);
    }

    /**
     * Add a new event
     */
    private void addEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        String eventTitle = request.getParameter("eventTitle");
        String eventDescription = request.getParameter("eventDescription");
        String eventDateStr = request.getParameter("eventDate");
        String eventLocation = request.getParameter("eventLocation");
        String eventPriceStr = request.getParameter("eventPrice");
        Part filePart = request.getPart("eventImage");

        // Validate input
        if (eventTitle == null || eventTitle.trim().isEmpty() ||
                eventDescription == null || eventDescription.trim().isEmpty() ||
                eventDateStr == null || eventDateStr.trim().isEmpty() ||
                eventLocation == null || eventLocation.trim().isEmpty() ||
                eventPriceStr == null || eventPriceStr.trim().isEmpty()) {

            request.setAttribute("error", "All fields except image are required");
            request.setAttribute("action", "add");
            request.getRequestDispatcher("/admin/manage-events.jsp").forward(request, response);
            return;
        }

        try {
            double eventPrice = Double.parseDouble(eventPriceStr);
            Date eventDate = Date.valueOf(eventDateStr);
            byte[] eventImage = null;

            // Process the uploaded image
            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream inputStream = filePart.getInputStream()) {
                    eventImage = inputStream.readAllBytes();
                }
            }

            Event event = new Event();
            event.setEventTitle(eventTitle);
            event.setEventDescription(eventDescription);
            event.setEventDate(eventDate);
            event.setEventLocation(eventLocation);
            event.setEventPrice(eventPrice);
            event.setUserId(user.getUserId());
            event.setEventImage(eventImage);

            int eventId = eventDAO.addEvent(event);

            if (eventId > 0) {
                response.sendRedirect(request.getContextPath() + "/admin/events/list?success=Event added successfully");
            } else {
                request.setAttribute("error", "Failed to add event");
                request.setAttribute("action", "add");
                request.getRequestDispatcher("/admin/manage-events.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid price format");
            request.setAttribute("action", "add");
            request.getRequestDispatcher("/admin/manage-events.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid date format");
            request.setAttribute("action", "add");
            request.getRequestDispatcher("/admin/manage-events.jsp").forward(request, response);
        }
    }

    /**
     * Update an existing event
     */
    private void updateEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        String eventTitle = request.getParameter("eventTitle");
        String eventDescription = request.getParameter("eventDescription");
        String eventDateStr = request.getParameter("eventDate");
        String eventLocation = request.getParameter("eventLocation");
        String eventPriceStr = request.getParameter("eventPrice");
        Part filePart = request.getPart("eventImage");

        // Validate input
        if (eventTitle == null || eventTitle.trim().isEmpty() ||
                eventDescription == null || eventDescription.trim().isEmpty() ||
                eventDateStr == null || eventDateStr.trim().isEmpty() ||
                eventLocation == null || eventLocation.trim().isEmpty() ||
                eventPriceStr == null || eventPriceStr.trim().isEmpty()) {

            request.setAttribute("error", "All fields except image are required");
            showEditForm(request, response);
            return;
        }

        try {
            Date eventDate = Date.valueOf(eventDateStr);
            double eventPrice = Double.parseDouble(eventPriceStr);

            Event event = eventDAO.getEventById(eventId);

            if (event != null) {
                event.setEventTitle(eventTitle);
                event.setEventDescription(eventDescription);
                event.setEventDate(eventDate);
                event.setEventLocation(eventLocation);
                event.setEventPrice(eventPrice);

                // Process the uploaded image if provided
                if (filePart != null && filePart.getSize() > 0) {
                    try (InputStream inputStream = filePart.getInputStream()) {
                        event.setEventImage(inputStream.readAllBytes());
                    }
                } // If no new image is uploaded, keep the existing image

                boolean success = eventDAO.updateEvent(event);

                if (success) {
                    request.setAttribute("success", "Event updated successfully");
                } else {
                    request.setAttribute("error", "Failed to update event");
                }
            } else {
                request.setAttribute("error", "Event not found");
            }

            listEvents(request, response);

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid date or price format");
            showEditForm(request, response);
        }
    }

    /**
     * Delete an event
     */
    private void deleteEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("id"));

        boolean success = eventDAO.deleteEvent(eventId);

        if (success) {
            request.setAttribute("success", "Event deleted successfully");
        } else {
            request.setAttribute("error", "Failed to delete event");
        }

        listEvents(request, response);
    }
}