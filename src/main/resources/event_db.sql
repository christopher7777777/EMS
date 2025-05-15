-- Stores user account details
CREATE TABLE users (
    users_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, -- Stores hashed password
    email VARCHAR(100) NOT NULL UNIQUE,
    role ENUM('ADMIN', 'CUSTOMER') NOT NULL,
    profile_picture MEDIUMBLOB,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

-- Stores event details created by users
CREATE TABLE event (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    event_title VARCHAR(100) NOT NULL,
    event_description TEXT NOT NULL,
    event_date DATE NOT NULL,
    event_location VARCHAR(255) NOT NULL,
    event_price DECIMAL(10, 2) NOT NULL,
    event_image MEDIUMBLOB,
    User_id INT NOT NULL, -- References the user who created the event
    FOREIGN KEY (User_id) REFERENCES users(users_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Stores booking details for events
CREATE TABLE booking (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_event_id INT NOT NULL, -- (Possibly redundant) event reference
    booking_name VARCHAR(100) NOT NULL,
    booking_email VARCHAR(100) NOT NULL,
    booking_phone VARCHAR(20) NOT NULL,
    booking_subject VARCHAR(255) NOT NULL,
    booking_meeting_time DATETIME NOT NULL,
    booking_address TEXT NOT NULL,
    booking_date DATE NOT NULL,
    Event_id INT NOT NULL, -- References the booked event
    FOREIGN KEY (Event_id) REFERENCES event(event_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Stores user feedback on events
CREATE TABLE feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    feedback_event_id INT NOT NULL, -- (Possibly redundant) event reference
    feedback_rating INT NOT NULL CHECK (feedback_rating BETWEEN 1 AND 5),
    feedback_comment TEXT NOT NULL,
    feedback_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Event_id INT NOT NULL, -- References the related event
    FOREIGN KEY (Event_id) REFERENCES event(event_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Stores contact messages from users
CREATE TABLE contact (
    contact_id INT AUTO_INCREMENT PRIMARY KEY,
    contact_name VARCHAR(100) NOT NULL,
    contact_email VARCHAR(100) NOT NULL,
    contact_subject VARCHAR(255) NOT NULL,
    contact_message TEXT NOT NULL,
    contact_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id INT, -- Optional reference to a user
    FOREIGN KEY (user_id) REFERENCES users(users_id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Stores blog posts related to events
CREATE TABLE blog (
    blog_id INT AUTO_INCREMENT PRIMARY KEY,
    blog_title VARCHAR(255) NOT NULL,
    blog_description TEXT NOT NULL,
    blog_post_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Event_id INT NOT NULL, -- References the related event
    FOREIGN KEY (Event_id) REFERENCES event(event_id) ON DELETE CASCADE
) ENGINE=InnoDB;
