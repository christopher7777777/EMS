package utils;

import model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter to protect admin resources from unauthorized access
 * Configuration is in web.xml
 */
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        String requestURI = httpRequest.getRequestURI();
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        boolean isAdminResource = requestURI.contains("/admin/");
        boolean isAdminLoginPage = requestURI.contains("/admin/login.jsp") || requestURI.endsWith("/admin/login");
        
        // Allow access to admin login page without authentication
        if (isAdminLoginPage) {
            chain.doFilter(request, response);
            return;
        }
        
        if (isLoggedIn) {
            User user = (User) session.getAttribute("user");
            
            // Check if trying to access admin resources
            if (isAdminResource && !user.isAdmin()) {
                // User is not an admin but trying to access admin resources
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/access-denied.jsp");
                return;
            }
            
            // User is authenticated and authorized, continue
            chain.doFilter(request, response);
        } else if (isAdminResource) {
            // Not logged in and trying to access admin resources
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/login");
        } else {
            // Not logged in but accessing public resources
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}
