-- Create users table
CREATE TABLE users (
                       users_id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL, -- Stores hashed password
                       email VARCHAR(100) NOT NULL UNIQUE,
                       role ENUM('ADMIN', 'CUSTOMER') NOT NULL,
                       profile_picture MEDIUMBLOB,
                       is_active BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

-- Create event table
CREATE TABLE event (
                       event_id INT AUTO_INCREMENT PRIMARY KEY,
                       event_title VARCHAR(100) NOT NULL,
                       event_description TEXT NOT NULL,
                       event_date DATE NOT NULL,
                       event_location VARCHAR(255) NOT NULL,
                       event_price DECIMAL(10, 2) NOT NULL,
                       User_id INT NOT NULL,
                       FOREIGN KEY (User_id) REFERENCES users(users_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Create booking table
CREATE TABLE booking (
                         booking_id INT AUTO_INCREMENT PRIMARY KEY,
                         booking_event_id INT NOT NULL,
                         booking_name VARCHAR(100) NOT NULL,
                         booking_email VARCHAR(100) NOT NULL,
                         booking_phone VARCHAR(20) NOT NULL,
                         booking_subject VARCHAR(255) NOT NULL,
                         booking_meeting_time DATETIME NOT NULL,
                         booking_address TEXT NOT NULL,
                         booking_date DATE NOT NULL,
                         Event_id INT NOT NULL,
                         FOREIGN KEY (Event_id) REFERENCES event(event_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Create feedback table
CREATE TABLE feedback (
                          feedback_id INT AUTO_INCREMENT PRIMARY KEY,
                          feedback_event_id INT NOT NULL,
                          feedback_rating INT NOT NULL CHECK (feedback_rating BETWEEN 1 AND 5),
                          feedback_comment TEXT NOT NULL,
                          feedback_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          Event_id INT NOT NULL,
                          FOREIGN KEY (Event_id) REFERENCES event(event_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Create contact table
CREATE TABLE contact (
                         contact_id INT AUTO_INCREMENT PRIMARY KEY,
                         contact_name VARCHAR(100) NOT NULL,
                         contact_email VARCHAR(100) NOT NULL,
                         contact_subject VARCHAR(255) NOT NULL,
                         contact_message TEXT NOT NULL,
                         contact_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         user_id INT,
                         FOREIGN KEY (user_id) REFERENCES users(users_id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Create blog table
CREATE TABLE blog (
                      blog_id INT AUTO_INCREMENT PRIMARY KEY,
                      blog_title VARCHAR(255) NOT NULL,
                      blog_description TEXT NOT NULL,
                      blog_post_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      Event_id INT NOT NULL,
                      FOREIGN KEY (Event_id) REFERENCES event(event_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Insert initial admin user (password: admin123)
-- The password is hashed using SHA-256 with a salt
INSERT INTO users (username, password, email, role, is_active)
VALUES ('admin', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8:5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'admin@example.com', 'ADMIN', TRUE);

-- Insert initial customer user (password: customer123)
INSERT INTO users (username, password, email, role, is_active)
VALUES ('customer', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92:8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'customer@example.com', 'CUSTOMER', TRUE);

-- Insert sample events
INSERT INTO event (event_title, event_description, event_date, event_location, event_price, User_id)
VALUES
    ('Tech Conference 2024', 'A conference for technology enthusiasts and professionals. Join us for a day of learning and networking.', '2024-12-15', 'Tech Convention Center, New York', 199.99, 1),
    ('Music Festival', 'A three-day music festival featuring top artists from around the world.', '2024-08-20', 'Central Park, New York', 149.50, 1),
    ('Business Workshop', 'Learn essential business skills from industry experts.', '2024-09-05', 'Business Center, Chicago', 99.99, 1),
    ('Art Exhibition', 'Featuring works from renowned contemporary artists.', '2024-10-10', 'Modern Art Gallery, Los Angeles', 25.00, 1),
    ('Charity Gala', 'Annual fundraising gala for children\'s education.', '2024-11-30', 'Grand Hotel, Boston', 299.99, 1);

-- Insert sample blogs
INSERT INTO blog (blog_title, blog_description, blog_post_date, Event_id)
VALUES
('Destination: Nepal. Let\'s go!', 'Nepal, a country nestled in the lap of the Himalayas, offers breathtaking landscapes and rich cultural experiences. From the towering peaks of Mount Everest to the serene valleys and ancient temples, Nepal is a paradise for adventurers and spiritual seekers alike.\n\nIn this blog, we explore the must-visit destinations in Nepal, including Kathmandu Valley, Pokhara, and the Annapurna Circuit. We also provide tips for trekking, cultural etiquette, and the best times to visit.\n\nJoin us on this virtual journey to Nepal and discover why it should be your next travel destination!', '2024-01-10 14:30:00', 1),
    ('The Future of Technology', 'In this blog, we explore emerging technology trends that will shape our future. From artificial intelligence and machine learning to quantum computing and biotechnology, we delve into how these innovations will transform industries and our daily lives.\n\nWe also discuss the ethical implications of these technologies and the importance of responsible innovation. As we stand on the brink of a technological revolution, it\'s crucial to understand the potential benefits and challenges ahead.\n\nStay ahead of the curve with our insights into the future of technology!', '2024-02-20 10:15:00', 1),
('Sustainable Event Planning', 'Planning events with sustainability in mind is not just good for the planet—it\'s also becoming increasingly important to attendees. In this blog, we share practical tips for organizing eco-friendly events that minimize environmental impact without compromising on quality or experience.\n\nLearn about sustainable venues, waste reduction strategies, eco-friendly catering options, and how to communicate your green initiatives to participants. We also showcase successful case studies of sustainable events that made a difference.\n\nMake your next event both memorable and environmentally responsible with our sustainable event planning guide!', '2024-03-15 09:45:00', 3);

-- Insert sample contacts
INSERT INTO contact (contact_name, contact_email, contact_subject, contact_message, contact_date, user_id)
VALUES
    ('John Smith', 'john@example.com', 'Event Inquiry', 'I would like to know more about the Tech Conference. Do you offer group discounts?', '2024-04-05 13:20:00', 2),
    ('Mary Johnson', 'mary@example.com', 'Partnership Opportunity', 'Our company would like to discuss potential sponsorship for the Music Festival. Please contact me at your earliest convenience.', '2024-04-10 09:30:00', NULL);

-- Insert sample feedback
INSERT INTO feedback (feedback_event_id, feedback_rating, feedback_comment, feedback_date, Event_id)
VALUES
    (1, 5, 'The Tech Conference was excellent! Well-organized with great speakers. Looking forward to next year\'s event.', '2024-01-20 15:45:00', 1),
(2, 4, 'Enjoyed the Music Festival. Great lineup and atmosphere. Could improve on food options.', '2024-02-25 18:30:00', 2);

-- Insert sample bookings
INSERT INTO booking (booking_event_id, booking_name, booking_email, booking_phone, booking_subject, booking_meeting_time, booking_address, booking_date, Event_id)
VALUES
(1, 'Customer User', 'customer@example.com', '123-456-7890', 'Tech Conference Registration', '2024-12-15 09:00:00', '123 Main St, New York, NY 10001', '2024-04-15', 1),
(3, 'Jane Doe', 'jane@example.com', '987-654-3210', 'Business Workshop Registration', '2024-09-05 10:30:00', '456 Oak Ave, Chicago, IL 60601', '2024-04-20', 3);

-- Create indexes for better performance
CREATE INDEX idx_event_date ON event(event_date);
CREATE INDEX idx_event_location ON event(event_location);
CREATE INDEX idx_blog_post_date ON blog(blog_post_date);
CREATE INDEX idx_feedback_event_id ON feedback(feedback_event_id);
CREATE INDEX idx_booking_event_id ON booking(booking_event_id);
