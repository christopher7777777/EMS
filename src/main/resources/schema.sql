CREATE DATABASE events_db;
USE events_db;
CREATE TABLE Users (
                       userId INT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       role ENUM('Customer', 'Admin') NOT NULL,
                       fullName VARCHAR(100),
                       profileImage BLOB
);

CREATE TABLE Events (
                        eventId INT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(100) NOT NULL,
                        description TEXT,
                        date DATE NOT NULL,
                        location VARCHAR(100),
                        category VARCHAR(50),
                        organizerId INT,
                        FOREIGN KEY (organizerId) REFERENCES Users(userId)
);

CREATE TABLE Bookings (
                          bookingId INT AUTO_INCREMENT PRIMARY KEY,
                          userId INT,
                          eventId INT,
                          status ENUM('Pending', 'Approved', 'Cancelled') DEFAULT 'Pending',
                          bookingDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (userId) REFERENCES Users(userId),
                          FOREIGN KEY (eventId) REFERENCES Events(eventId)
);

CREATE TABLE Feedback (
                          feedbackId INT AUTO_INCREMENT PRIMARY KEY,
                          userId INT,
                          eventId INT,
                          rating INT CHECK (rating >= 1 AND rating <= 5),
                          comment TEXT,
                          feedbackDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (userId) REFERENCES Users(userId),
                          FOREIGN KEY (eventId) REFERENCES Events(eventId)
);

CREATE TABLE ContactUsMessages (
                                   messageId INT AUTO_INCREMENT PRIMARY KEY,
                                   name VARCHAR(100),
                                   email VARCHAR(100),
                                   message TEXT,
                                   submittedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
