package servlets.admin;

import dao.BlogDAO;
import dao.EventDAO;
import model.Blog;
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
import java.sql.Timestamp;
import java.util.List;

/**
 * Servlet for managing blogs (CRUD operations)
 */
@WebServlet("/admin/blogs/*")
@MultipartConfig(maxFileSize = 5242880) // 5MB max file size
public class BlogManagementServlet extends HttpServlet {

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
                listBlogs(request, response);
                break;
            case "/add":
                showAddForm(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/delete":
                deleteBlog(request, response);
                break;
            default:
                listBlogs(request, response);
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
                addBlog(request, response);
                break;
            case "/update":
                updateBlog(request, response);
                break;
            default:
                listBlogs(request, response);
                break;
        }
    }

    /**
     * List all blogs
     */
    private void listBlogs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Blog> blogs = blogDAO.getAllBlogs();
        request.setAttribute("blogs", blogs);
        request.getRequestDispatcher("/admin/blog-management.jsp").forward(request, response);
    }

    /**
     * Show the add blog form
     */
    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get all events for associating with blog
        List<Event> events = eventDAO.getAllEvents();
        request.setAttribute("events", events);
        request.setAttribute("action", "add");
        request.getRequestDispatcher("/admin/blog-management.jsp").forward(request, response);
    }

    /**
     * Show the edit blog form
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int blogId = Integer.parseInt(request.getParameter("id"));
        Blog blog = blogDAO.getBlogById(blogId);

        // Get all events for associating with blog
        List<Event> events = eventDAO.getAllEvents();

        request.setAttribute("blog", blog);
        request.setAttribute("events", events);
        request.setAttribute("action", "edit");
        request.getRequestDispatcher("/admin/blog-management.jsp").forward(request, response);
    }

    /**
     * Add a new blog
     */
    private void addBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String blogTitle = request.getParameter("blogTitle");
        String blogDescription = request.getParameter("blogDescription");
        String eventIdStr = request.getParameter("eventId");
        Part filePart = request.getPart("blogImage");

        // Validate input
        if (blogTitle == null || blogTitle.trim().isEmpty() ||
                blogDescription == null || blogDescription.trim().isEmpty() ||
                eventIdStr == null || eventIdStr.trim().isEmpty()) {

            request.setAttribute("error", "All fields except image are required.");
            showAddForm(request, response);
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdStr);

            Blog blog = new Blog();
            blog.setBlogTitle(blogTitle);
            blog.setBlogDescription(blogDescription);
            blog.setBlogPostDate(new Timestamp(System.currentTimeMillis()));
            blog.setEventId(eventId);

            // Handle image upload
            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream input = filePart.getInputStream()) {
                    byte[] imageBytes = input.readAllBytes();
                    blog.setBlogImage(imageBytes);
                }
            }

            int blogId = blogDAO.addBlog(blog);

            if (blogId > 0) {
                request.setAttribute("success", "Blog added successfully.");
            } else {
                request.setAttribute("error", "Failed to add blog.");
            }

            listBlogs(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid event ID.");
            showAddForm(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error adding blog: " + e.getMessage());
            showAddForm(request, response);
        }
    }

    /**
     * Update an existing blog
     */
    private void updateBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int blogId = Integer.parseInt(request.getParameter("blogId"));
        String blogTitle = request.getParameter("blogTitle");
        String blogDescription = request.getParameter("blogDescription");
        Part filePart = request.getPart("blogImage");

        // Validate input
        if (blogTitle == null || blogTitle.trim().isEmpty() ||
                blogDescription == null || blogDescription.trim().isEmpty()) {

            request.setAttribute("error", "Title and description are required.");
            showEditForm(request, response);
            return;
        }

        try {
            Blog blog = blogDAO.getBlogById(blogId);

            if (blog != null) {
                blog.setBlogTitle(blogTitle);
                blog.setBlogDescription(blogDescription);

                // Handle image upload
                if (filePart != null && filePart.getSize() > 0) {
                    try (InputStream input = filePart.getInputStream()) {
                        byte[] imageBytes = input.readAllBytes();
                        blog.setBlogImage(imageBytes);
                    }
                }

                boolean success = blogDAO.updateBlog(blog);

                if (success) {
                    request.setAttribute("success", "Blog updated successfully.");
                } else {
                    request.setAttribute("error", "Failed to update blog.");
                }
            } else {
                request.setAttribute("error", "Blog not found.");
            }

            listBlogs(request, response);

        } catch (Exception e) {
            request.setAttribute("error", "Error updating blog: " + e.getMessage());
            showEditForm(request, response);
        }
    }

    /**
     * Delete a blog
     */
    private void deleteBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int blogId = Integer.parseInt(request.getParameter("id"));

        boolean success = blogDAO.deleteBlog(blogId);

        if (success) {
            request.setAttribute("success", "Blog deleted successfully.");
        } else {
            request.setAttribute("error", "Failed to delete blog.");
        }

        listBlogs(request, response);
    }
}