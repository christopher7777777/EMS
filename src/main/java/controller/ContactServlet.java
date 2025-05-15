package controller;

import dao.ContactDAO;
import model.Contact;
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
 * Servlet for handling contact form submissions
 */
@WebServlet("/contact")
public class ContactServlet extends HttpServlet {
    
    private ContactDAO contactDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        contactDAO = new ContactDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Display contact form
        request.getRequestDispatcher("/contact.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Process contact form submission
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");
        
        // Validate input
        if (name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            subject == null || subject.trim().isEmpty() ||
            message == null || message.trim().isEmpty()) {
            
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/contact.jsp").forward(request, response);
            return;
        }
        
        // Check for logged in user
        HttpSession session = request.getSession(false);
        Integer userId = null;
        
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            userId = user.getUserId();
        }
        
        // Create contact message
        Contact contact = new Contact();
        contact.setContactName(name);
        contact.setContactEmail(email);
        contact.setContactSubject(subject);
        contact.setContactMessage(message);
        contact.setContactDate(new Timestamp(System.currentTimeMillis()));
        contact.setUserId(userId);
        
        int contactId = contactDAO.addContact(contact);
        
        if (contactId > 0) {
            request.setAttribute("success", "Your message has been sent successfully.");
        } else {
            request.setAttribute("error", "Failed to send message. Please try again.");
        }
        
        request.getRequestDispatcher("/contact.jsp").forward(request, response);
    }
}
