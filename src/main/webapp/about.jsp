<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About Us - Event Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <!-- Include Header -->
    <jsp:include page="/includes/header.jsp" />
    
    <main class="main-content">
        <section class="about-section">
            <div class="container">
                <h1 class="section-title">About Us</h1>
                
                <div class="about-content">
                    <div class="about-image">
                        <div class="about-image-placeholder">
                            <svg xmlns="http://www.w3.org/2000/svg" width="80" height="80" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <circle cx="12" cy="12" r="10"></circle>
                                <line x1="12" y1="8" x2="12" y2="16"></line>
                                <line x1="8" y1="12" x2="16" y2="12"></line>
                            </svg>
                        </div>
                    </div>
                    
                    <div class="about-text">
                        <h2>Our Story</h2>
                        <p>Welcome to our Event Management System, a premier platform for organizing and managing all types of events. Founded with a passion for creating memorable experiences, we strive to connect people through well-organized events.</p>
                        
                        <h2>Our Mission</h2>
                        <p>Our mission is to simplify the event management process by providing an easy-to-use platform that helps organizers create, manage, and promote their events while allowing attendees to discover and book events that interest them.</p>
                        
                        <h2>What We Offer</h2>
                        <div class="services-grid">
                            <div class="service-card">
                                <h3>Professional Planning</h3>
                                <p>Our team of experienced event planners ensures every detail is carefully considered and executed.</p>
                            </div>
                            
                            <div class="service-card">
                                <h3>Diverse Event Types</h3>
                                <p>From corporate conferences to social gatherings, we handle events of all types and sizes.</p>
                            </div>
                            
                            <div class="service-card">
                                <h3>Seamless Booking</h3>
                                <p>Our platform makes it easy for attendees to find, book, and manage their event registrations.</p>
                            </div>
                            
                            <div class="service-card">
                                <h3>Feedback & Improvement</h3>
                                <p>We value customer feedback and continuously improve our services based on your input.</p>
                            </div>
                        </div>
                        
                        <h2>Our Team</h2>
                        <p>Our dedicated team consists of experienced event planners, creative designers, and technical experts who work together to deliver exceptional event experiences.</p>
                        
                        <div class="cta-box">
                            <h3>Ready to plan your next event?</h3>
                            <p>Join us today and experience the difference of professional event management.</p>
                            <a href="${pageContext.request.contextPath}/register" class="btn btn-primary">Sign Up Now</a>
                            <a href="${pageContext.request.contextPath}/contact" class="btn btn-secondary">Contact Us</a>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </main>
    
    <!-- Include Footer -->
    <jsp:include page="/includes/footer.jsp" />
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
