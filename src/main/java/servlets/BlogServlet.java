package servlets;

import dao.BlogDAO;
import dao.EventDAO;
import model.Blog;
import model.Event;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling blog posts (listing and details)
 */
@WebServlet("/blogs/*")
public class BlogServlet extends HttpServlet {
    
    private BlogDAO blogDAO;
    private EventDAO eventDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        blogDAO = new BlogDAO();
        eventDAO = new EventDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        
        if (action == null || action.equals("/")) {
            listBlogs(request, response);
        } else if (action.equals("/detail")) {
            showBlogDetails(request, response);
        } else if (action.equals("/search")) {
            searchBlogs(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/blogs");
        }
    }
    
    /**
     * List all blogs with pagination
     */
    private void listBlogs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get page parameters
        int page = 1;
        int recordsPerPage = 5;
        
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
        
        // Get blogs for current page
        List<Blog> blogs = blogDAO.getPaginatedBlogs(offset, recordsPerPage);
        
        // Count total blogs for pagination
        int totalBlogs = blogDAO.countTotalBlogs();
        int totalPages = (int) Math.ceil(totalBlogs * 1.0 / recordsPerPage);
        
        request.setAttribute("blogs", blogs);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        
        request.getRequestDispatcher("/blog.jsp").forward(request, response);
    }
    
    /**
     * Show details for a specific blog
     */
    private void showBlogDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String blogIdStr = request.getParameter("id");
        
        if (blogIdStr == null || blogIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/blogs");
            return;
        }
        
        try {
            int blogId = Integer.parseInt(blogIdStr);
            Blog blog = blogDAO.getBlogById(blogId);
            
            if (blog == null) {
                response.sendRedirect(request.getContextPath() + "/blogs");
                return;
            }
            
            // Get the associated event if available
            Event event = eventDAO.getEventById(blog.getEventId());
            
            request.setAttribute("blog", blog);
            request.setAttribute("event", event);
            request.getRequestDispatcher("/blog-detail.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/blogs");
        }
    }
    
    /**
     * Search for blogs
     */
    private void searchBlogs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        
        if (keyword == null || keyword.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/blogs");
            return;
        }
        
        List<Blog> searchResults = blogDAO.searchBlogs(keyword);
        
        request.setAttribute("blogs", searchResults);
        request.setAttribute("keyword", keyword);
        request.setAttribute("isSearchResult", true);
        
        request.getRequestDispatcher("/blog.jsp").forward(request, response);
    }
}
