package servlets;

import dao.BookingDAO;
import dao.EventDAO;
import model.Booking;
import model.Event;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Servlet for handling event bookings
 */
@WebServlet("/booking/*")
public class BookingServlet extends HttpServlet {
    
    private BookingDAO bookingDAO;
    private EventDAO eventDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        bookingDAO = new BookingDAO();
        eventDAO = new EventDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }
        
        switch (action) {
            case "/form":
                showBookingForm(request, response);
                break;
            case "/list":
                listUserBookings(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/events");
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/create";
        }
        
        switch (action) {
            case "/create":
                createBooking(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/events");
                break;
        }
    }
    
    /**
     * Show the booking form for an event
     */
    private void showBookingForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            request.getRequestDispatcher("/customer/booking-form.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/events");
        }
    }
    
    /**
     * Create a new booking
     */
    private void createBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // Check if user is logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        
        String eventIdStr = request.getParameter("eventId");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String subject = request.getParameter("subject");
        String meetingTimeStr = request.getParameter("meetingTime");
        String address = request.getParameter("address");
        
        // Validate input
        if (eventIdStr == null || eventIdStr.trim().isEmpty() ||
            name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty() ||
            subject == null || subject.trim().isEmpty() ||
            meetingTimeStr == null || meetingTimeStr.trim().isEmpty() ||
            address == null || address.trim().isEmpty()) {
            
            request.setAttribute("error", "All fields are required.");
            showBookingForm(request, response);
            return;
        }
        
        try {
            int eventId = Integer.parseInt(eventIdStr);
            Event event = eventDAO.getEventById(eventId);
            
            if (event == null) {
                response.sendRedirect(request.getContextPath() + "/events");
                return;
            }
            
            // Parse meeting time
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            java.util.Date parsedDate = dateFormat.parse(meetingTimeStr);
            Timestamp meetingTime = new Timestamp(parsedDate.getTime());
            
            // Create booking
            Booking booking = new Booking();
            booking.setBookingEventId(eventId);
            booking.setBookingName(name);
            booking.setBookingEmail(email);
            booking.setBookingPhone(phone);
            booking.setBookingSubject(subject);
            booking.setBookingMeetingTime(meetingTime);
            booking.setBookingAddress(address);
            booking.setBookingDate(new Date(System.currentTimeMillis()));
            booking.setEventId(eventId);
            
            int bookingId = bookingDAO.addBooking(booking);
            
            if (bookingId > 0) {
                request.setAttribute("success", "Booking created successfully.");
            } else {
                request.setAttribute("error", "Failed to create booking.");
            }
            
            // Redirect to events page
            response.sendRedirect(request.getContextPath() + "/events");
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid event ID.");
            showBookingForm(request, response);
        } catch (ParseException e) {
            request.setAttribute("error", "Invalid meeting time format.");
            showBookingForm(request, response);
        }
    }
    
    /**
     * List all bookings for the current user
     */
    private void listUserBookings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // Check if user is logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        
        // In this simple implementation, we assume bookings are tracked by events
        // A more complete implementation would have a user_id field in bookings
        
        // For now, we'll just show all bookings to demonstrate
        request.setAttribute("bookings", bookingDAO.getAllBookings());
        request.getRequestDispatcher("/customer/bookings.jsp").forward(request, response);
    }
}
