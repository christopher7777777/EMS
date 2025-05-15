package controller.admin;

import dao.BookingDAO;
import model.Booking;
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
 * Servlet for managing bookings (CRUD operations)
 */
@WebServlet("/admin/bookings/*")
public class ManageBookingsServlet extends HttpServlet {

    private BookingDAO bookingDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        bookingDAO = new BookingDAO();
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
                listBookings(request, response);
                break;
            case "/delete":
                deleteBooking(request, response);
                break;
            default:
                listBookings(request, response);
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

    }

    /**
     * List all bookings
     */
    private void listBookings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Booking> bookings = bookingDAO.getAllBookings();
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("/admin/manage-bookings.jsp").forward(request, response);
    }

    /**
     * Delete a booking
     */
    private void deleteBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookingId = Integer.parseInt(request.getParameter("id"));
        Booking booking = bookingDAO.getBookingById(bookingId);
        if (booking == null) {
            request.setAttribute("error", "Booking not found.");
            listBookings(request, response);
            return;
        }

        boolean success = bookingDAO.deleteBooking(bookingId);
        if (success) {
            request.setAttribute("success", "Booking deleted successfully.");
        } else {
            request.setAttribute("error", "Failed to delete booking.");
        }

        listBookings(request, response);
    }
}