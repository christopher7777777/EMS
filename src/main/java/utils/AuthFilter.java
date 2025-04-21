package utils;

import model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

/**
 * Filter to protect resources from unauthorized access and restrict specific roles from dashboards
 * Configuration is in web.xml
 */
public class AuthFilter implements Filter {

    // Set of roles restricted from accessing dashboards
    private static final Set<String> RESTRICTED_ROLES = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialize restricted roles (could be loaded from config if dynamic)
        RESTRICTED_ROLES.add("restricted");
        RESTRICTED_ROLES.add("guest");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        boolean isAdminResource = requestURI.contains("/admin/");
        boolean isDashboardResource = requestURI.contains("/dashboard/");
        boolean isAdminLoginPage = requestURI.contains("/admin/login.jsp") || requestURI.endsWith("/admin/login");

        // Allow access to admin login page without authentication
        if (isAdminLoginPage) {
            chain.doFilter(request, response);
            return;
        }

        if (isLoggedIn) {
            User user = (User) session.getAttribute("user");
            String userRole = String.valueOf(user.getRole()); // Assumes User has getRole() returning String

            // Check if user has a restricted role
            if (RESTRICTED_ROLES.contains(userRole)) {
                // Restricted roles cannot access any dashboard
                if (isAdminResource || isDashboardResource) {
                    session.setAttribute("errorMessage",
                            "Access Denied: Your role (" + userRole + ") is not permitted to access dashboards.");
                    httpResponse.sendRedirect(contextPath + "/access-denied.jsp");
                    return;
                }
            }

            // Check if trying to access admin resources
            if (isAdminResource && !user.isAdmin()) {
                session.setAttribute("errorMessage",
                        "Access Denied: Administrator privileges required.");
                httpResponse.sendRedirect(contextPath + "/access-denied.jsp");
                return;
            }

            // User is authenticated and authorized, proceed
            chain.doFilter(request, response);
        } else if (isAdminResource) {
            // Not logged in and trying to access admin resources
            httpResponse.sendRedirect(contextPath + "/admin/login");
        } else {
            // Not logged in but accessing public resources
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
        RESTRICTED_ROLES.clear();
    }
}