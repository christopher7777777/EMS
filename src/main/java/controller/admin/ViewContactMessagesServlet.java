package controller.admin;

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
import java.util.List;

/**
 * Servlet for viewing contact messages as admin
 */
@WebServlet("/admin/contacts")
public class ViewContactMessagesServlet extends HttpServlet {
    
    private ContactDAO contactDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        contactDAO = new ContactDAO();
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
        
        List<Contact> contacts = contactDAO.getAllContacts();
        request.setAttribute("contacts", contacts);
        
        request.getRequestDispatcher("/admin/view-contact-messages.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle delete contact if needed
        String action = request.getParameter("action");
        
        if ("delete".equals(action)) {
            int contactId = Integer.parseInt(request.getParameter("contactId"));
            boolean success = contactDAO.deleteContact(contactId);
            
            if (success) {
                request.setAttribute("success", "Contact message deleted successfully.");
            } else {
                request.setAttribute("error", "Failed to delete contact message.");
            }
            
            doGet(request, response);
        }
    }
}
