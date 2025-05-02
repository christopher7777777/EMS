package servlets;

import dao.BlogDAO;
import model.Blog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Servlet to serve blog images
 */
@WebServlet("/image")
public class ImageServlet extends HttpServlet {

    private BlogDAO blogDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        blogDAO = new BlogDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String blogIdStr = request.getParameter("blogId");

        if (blogIdStr == null || blogIdStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Blog ID is required");
            return;
        }

        try {
            int blogId = Integer.parseInt(blogIdStr);
            Blog blog = blogDAO.getBlogById(blogId);

            if (blog == null || blog.getBlogImage() == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
                return;
            }

            // Set response content type
            response.setContentType("image/jpeg"); // Adjust based on your image types
            response.setContentLength(blog.getBlogImage().length);

            // Write image to response
            try (OutputStream out = response.getOutputStream()) {
                out.write(blog.getBlogImage());
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Blog ID");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving image");
        }
    }
}