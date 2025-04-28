package servlets.admin;

import dao.EventDAO;
import model.Event;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Servlet to serve event images
 */
@WebServlet("/event/image")
public class EventImageServlet extends HttpServlet {

    private EventDAO eventDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        eventDAO = new EventDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("id"));
        Event event = eventDAO.getEventById(eventId);

        if (event != null && event.getEventImage() != null) {
            response.setContentType("image/jpeg"); // Adjust MIME type as needed
            response.setContentLength(event.getEventImage().length);
            try (OutputStream out = response.getOutputStream()) {
                out.write(event.getEventImage());
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
        }
    }
}