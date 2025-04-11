CREATE DATABASE events_db;
USE events_db;

CREATE TABLE Users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       email VARCHAR(255) UNIQUE,
                       password VARCHAR(255),
                       role ENUM('Admin', 'Customer'),
                       image VARCHAR(255)
);

CREATE TABLE Events (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(255),
                        date DATE,


CREATE TABLE Bookings (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          customer_id INT,
                          event_id INT,
                          booking_date DATE,
                          FOREIGN KEY (customer_id) REFERENCES Users(id),
                          FOREIGN KEY (event_id) REFERENCES Events(id)
);

CREATE TABLE EventLists (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            event_id INT,
                            list_name VARCHAR(255),
                            FOREIGN KEY (event_id) REFERENCES Events(id)
);

CREATE TABLE EventBookHistory (
                                  id INT PRIMARY KEY AUTO_INCREMENT,
                                  booking_id INT,
                                  action ENUM('Booked', 'Canceled'),
                                  timestamp DATETIME,
                                  FOREIGN KEY (booking_id) REFERENCES Bookings(id)
);

CREATE TABLE ContactUsMessages (
                                   id INT PRIMARY KEY AUTO_INCREMENT,
                                   name VARCHAR(255),
                                   message TEXT,
                                   timestamp DATETIME
);
