<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Welcome | Event Management System</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%-- Include Customer Header --%>
<%--<jsp:include page="/includes/customer-header.jsp" />--%>

<div class="hero-section">
  <div class="container">
    <div class="hero-content">
      <h1>Discover & Book Amazing Events</h1>
      <p>Find the perfect events for every occasion. From corporate meetings to casual gatherings, we've got you covered.</p>
      <div class="hero-buttons">
        <a href="${pageContext.request.contextPath}/events" class="btn btn-primary">Browse Events</a>
        <% if (session.getAttribute("userId") == null) { %>
        <a href="${pageContext.request.contextPath}/register" class="btn btn-secondary">Register Now</a>
        <% } %>
      </div>
    </div>
  </div>
</div>

<main class="container">
  <section class="features-section">
    <h2 class="section-title">Why Choose Us</h2>
    <div class="features">
      <div class="feature-card">
        <div class="feature-icon">
          <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-calendar">
            <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
            <line x1="16" y1="2" x2="16" y2="6"></line>
            <line x1="8" y1="2" x2="8" y2="6"></line>
            <line x1="3" y1="10" x2="21" y2="10"></line>
          </svg>
        </div>
        <h3>Wide Range of Events</h3>
        <p>Explore a diverse selection of events tailored to your interests and preferences.</p>
      </div>

      <div class="feature-card">
        <div class="feature-icon">
          <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-clock">
            <circle cx="12" cy="12" r="10"></circle>
            <polyline points="12 6 12 12 16 14"></polyline>
          </svg>
        </div>
        <h3>Easy Booking Process</h3>
        <p>Book your events quickly and securely with our simple booking system.</p>
      </div>

      <div class="feature-card">
        <div class="feature-icon">
          <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
          </svg>
        </div>
        <h3>User-Friendly Platform</h3>
        <p>Navigate through our intuitive platform designed to enhance your event experience.</p>
      </div>
    </div>
  </section>

  <section class="cta-section">
    <div class="cta-content">
      <h2>Ready to Experience Our Events?</h2>
      <p>Join thousands of satisfied customers who have already booked with us.</p>
      <a href="${pageContext.request.contextPath}/events" class="btn btn-primary">Browse Events Now</a>
    </div>
  </section>

  <section class="testimonials-section">
    <h2 class="section-title">What Our Customers Say</h2>
    <div class="testimonials">
      <div class="testimonial-card">
        <div class="testimonial-rating">
          <span class="star filled">★</span>
          <span class="star filled">★</span>
          <span class="star filled">★</span>
          <span class="star filled">★</span>
          <span class="star filled">★</span>
        </div>
        <p class="testimonial-text">"The event was perfectly organized, and the booking process was seamless. I highly recommend their services to anyone planning an event."</p>
        <div class="testimonial-author">
          <div class="author-name">John Doe</div>
          <div class="author-role">Corporate Event</div>
        </div>
      </div>

      <div class="testimonial-card">
        <div class="testimonial-rating">
          <span class="star filled">★</span>
          <span class="star filled">★</span>
          <span class="star filled">★</span>
          <span class="star filled">★</span>
          <span class="star">★</span>
        </div>
        <p class="testimonial-text">"I was amazed by the variety of events available. Found exactly what I was looking for and had a great experience."</p>
        <div class="testimonial-author">
          <div class="author-name">Jane Smith</div>
          <div class="author-role">Social Gathering</div>
        </div>
      </div>

      <div class="testimonial-card">
        <div class="testimonial-rating">
          <span class="star filled">★</span>
          <span class="star filled">★</span>
          <span class="star filled">★</span>
          <span class="star filled">★</span>
          <span class="star filled">★</span>
        </div>
        <p class="testimonial-text">"The customer service was exceptional. They addressed all my queries promptly and made sure everything went smoothly."</p>
        <div class="testimonial-author">
          <div class="author-name">Michael Johnson</div>
          <div class="author-role">Wedding Event</div>
        </div>
      </div>
    </div>
  </section>
</main>

<%-- Include Customer Footer --%>
<%--<jsp:include page="/includes/customer-footer.jsp" />--%>

<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
