<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About Us - Event Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>

        /* Team member styles */
        .team-section {
            margin: 60px 0;
        }

        .team-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 30px;
            margin-top: 40px;
        }

        .team-member {
            text-align: center;
        }

        .team-member img {
            width: 100%;
            height: auto;
            aspect-ratio: 1/1;
            object-fit: cover;
            margin-bottom: 15px;
        }

        .team-member h3 {
            margin: 0 0 5px 0;
            font-size: 1.2rem;
        }

        .team-member p {
            margin: 0;
            color: #666;
        }

        /* Responsive adjustments */
        @media (max-width: 992px) {
            .team-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }

        @media (max-width: 768px) {
            .about-content {
                flex-direction: column;
            }

            .about-image {
                max-width: 100%;
                margin-bottom: 30px;
            }
        }

        @media (max-width: 480px) {
            .team-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
<!-- Include Header -->
<jsp:include page="/includes/header.jsp" />

<main class="main-content">
    <section class="about-section">
        <div class="container">
            <h1 class="section-title">About Us</h1>

            <div class="about-content">
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
                </div>
            </div>

            <!-- Team Section -->
            <section class="team-section">
                <h2 class="section-title">Meet Our Team</h2>
                <p class="section-subtitle">Our dedicated team of experts works together to deliver exceptional event experiences.</p>

                <div class="team-grid">
                    <div class="team-member">
                        <img src="${pageContext.request.contextPath}/images/img.png" alt="Dhoni">
                        <h3>Dhoni</h3>
                        <p>Technical Director</p>
                        <p>New York, NY</p>
                    </div>

                    <div class="team-member">
                        <img src="${pageContext.request.contextPath}/images/img_3.png" alt="Watson">
                        <h3>Shane Watson</h3>
                        <p>Operations Director</p>
                        <p>San Francisco, CA</p>
                    </div>

                    <div class="team-member">
                        <img src="${pageContext.request.contextPath}/images/img_2.png" alt="Gayle">
                        <h3>Gayle</h3>
                        <p>Creative Director</p>
                        <p>Chicago, IL</p>
                    </div>

                    <div class="team-member">
                        <img src="${pageContext.request.contextPath}/images/img_1.png" alt="Raina">
                        <h3>Raina</h3>
                        <p>Marketing Director</p>
                        <p>Austin, TX</p>
                    </div>
                </div>
            </section>

            <div class="cta-box">
                <h3>Ready to plan your next event?</h3>
                <p>Join us today and experience the difference of professional event management.</p>
                <a href="${pageContext.request.contextPath}/register" class="btn btn-primary">Sign Up Now</a>
                <a href="${pageContext.request.contextPath}/contact" class="btn btn-secondary">Contact Us</a>
            </div>
        </div>
    </section>
</main>

<!-- Include Footer -->
<jsp:include page="/includes/footer.jsp" />

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>