package controller;

import dao.EventDAO;
import dao.BlogDAO;
import model.Event;
import model.Blog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet handling the homepage
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"", "/home"})
public class HomeServlet extends HttpServlet {
    
    private EventDAO eventDAO;
    private BlogDAO blogDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        eventDAO = new EventDAO();
        blogDAO = new BlogDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get featured/latest events (limit to 3 for the homepage)
        List<Event> featuredEvents = eventDAO.getPaginatedEvents(0, 3);
        request.setAttribute("featuredEvents", featuredEvents);
        
        // Get latest blogs (limit to 2 for the homepage)
        List<Blog> latestBlogs = blogDAO.getPaginatedBlogs(0, 2);
        request.setAttribute("latestBlogs", latestBlogs);
        
        // Forward to the home page
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
