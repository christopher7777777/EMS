CREATE DATABASE events_db;
USE events_db;

CREATE TABLE users (
                       users_id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(64) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       role ENUM('admin', 'customer') NOT NULL,
                       profile_picture VARCHAR(255),
                       is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE contact (
                         contact_id INT AUTO_INCREMENT PRIMARY KEY,
                         contact_name VARCHAR(100) NOT NULL,
                         contact_email VARCHAR(100) NOT NULL,
                         contact_subject VARCHAR(255) NOT NULL,
                         contact_message TEXT NOT NULL,
                         contact_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                         user_id INT,
                         FOREIGN KEY (user_id) REFERENCES users(users_id)
);

CREATE TABLE event (
                       event_id INT AUTO_INCREMENT PRIMARY KEY,
                       event_title VARCHAR(255) NOT NULL,
                       event_description TEXT NOT NULL,
                       event_date DATE NOT NULL,
                       event_location VARCHAR(255) NOT NULL,
                       event_price DECIMAL(10, 2) NOT NULL,
                       user_id INT,
                       FOREIGN KEY (user_id) REFERENCES users(users_id)
);

CREATE TABLE booking (
                         booking_id INT AUTO_INCREMENT PRIMARY KEY,
                         booking_event_id INT NOT NULL,
                         booking_name VARCHAR(100) NOT NULL,
                         booking_email VARCHAR(100) NOT NULL,
                         booking_phone VARCHAR(20) NOT NULL,
                         booking_subject VARCHAR(255) NOT NULL,
                         booking_meeting_time DATETIME NOT NULL,
                         booking_address VARCHAR(255) NOT NULL,
                         booking_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                         event_id INT,
                         FOREIGN KEY (event_id) REFERENCES event(event_id)
);

CREATE TABLE feedback (
                          feedback_id INT AUTO_INCREMENT PRIMARY KEY,
                          feedback_event_id INT NOT NULL,
                          feedback_rating INT NOT NULL CHECK (feedback_rating BETWEEN 1 AND 5),
                          feedback_comment TEXT,
                          feedback_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                          event_id INT,
                          FOREIGN KEY (event_id) REFERENCES event(event_id)
);

CREATE TABLE blog (
                      blog_id INT AUTO_INCREMENT PRIMARY KEY,
                      blog_title VARCHAR(255) NOT NULL,
                      blog_description TEXT NOT NULL,
                      blog_post_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                      event_id INT,
                      FOREIGN KEY (event_id) REFERENCES event(event_id)
);