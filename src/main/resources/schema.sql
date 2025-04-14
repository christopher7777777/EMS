CREATE DATABASE IF NOT EXISTS event_db;
USE event_db;

CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(64) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       role ENUM('admin', 'customer') NOT NULL,
                       profile_picture VARCHAR(255),
                       is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE events (
                       event_id INT AUTO_INCREMENT PRIMARY KEY,
                       event_title VARCHAR(100) NOT NULL,
                       event_description TEXT NOT NULL,
                       event_date DATETIME NOT NULL,
                       event_location VARCHAR(255) NOT NULL,
                       event_price DECIMAL(10, 2) NOT NULL,
                       user_id INT,
                       FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE booking (
                         booking_id INT AUTO_INCREMENT PRIMARY KEY,
                         booking_name VARCHAR(100) NOT NULL,
                         booking_email VARCHAR(100) NOT NULL,
                         booking_phone VARCHAR(20) NOT NULL,
                         booking_subject VARCHAR(100) NOT NULL,
                         booking_meeting_time DATETIME NOT NULL,
                         booking_address TEXT NOT NULL,
                         booking_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                         event_id INT,
                         FOREIGN KEY (event_id) REFERENCES events(event_id)
);

CREATE TABLE feedback (
                          feedback_id INT AUTO_INCREMENT PRIMARY KEY,
                          feedback_rating INT NOT NULL,
                          feedback_comment TEXT NOT NULL,
                          feedback_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                          event_id INT,
                          FOREIGN KEY (event_id) REFERENCES events(event_id)

);

CREATE TABLE  contact (
                          contact_id INT AUTO_INCREMENT PRIMARY KEY,
                          contact_name VARCHAR(100) NOT NULL,
                          contact_email VARCHAR(100) NOT NULL,
                          contact_subject VARCHAR(100) NOT NULL,
                          contact_message TEXT NOT NULL,
                          contact_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                          user_id INT,
                          FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE blog (
                      blog_id INT AUTO_INCREMENT PRIMARY KEY,
                      blog_title VARCHAR(100) NOT NULL,
                      blog_description TEXT NOT NULL,
                      blog_post_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                      event_id INT,
                      FOREIGN KEY (event_id) REFERENCES events(event_id)
);