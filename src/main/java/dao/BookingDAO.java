package dao;

import model.Booking;
import utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Booking entity
 */
public class BookingDAO {

    /**
     * Add a new booking to the database
     * @param booking Booking object to be added
     * @return the ID of the newly added booking, or -1 if operation failed
     */
    public int addBooking(Booking booking) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "INSERT INTO booking (booking_event_id, booking_name, booking_email, booking_phone, booking_subject, booking_meeting_time, booking_address, booking_date, Event_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, booking.getBookingEventId());
            statement.setString(2, booking.getBookingName());
            statement.setString(3, booking.getBookingEmail());
            statement.setString(4, booking.getBookingPhone());
            statement.setString(5, booking.getBookingSubject());
            statement.setTimestamp(6, booking.getBookingMeetingTime());
            statement.setString(7, booking.getBookingAddress());
            statement.setDate(8, booking.getBookingDate());
            statement.setInt(9, booking.getEventId());
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                return -1;
            }
            
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Get a booking by ID
     * @param bookingId the ID of the booking to retrieve
     * @return Booking object if found, null otherwise
     */
    public Booking getBookingById(int bookingId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM booking WHERE booking_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, bookingId);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingId(resultSet.getInt("booking_id"));
                booking.setBookingEventId(resultSet.getInt("booking_event_id"));
                booking.setBookingName(resultSet.getString("booking_name"));
                booking.setBookingEmail(resultSet.getString("booking_email"));
                booking.setBookingPhone(resultSet.getString("booking_phone"));
                booking.setBookingSubject(resultSet.getString("booking_subject"));
                booking.setBookingMeetingTime(resultSet.getTimestamp("booking_meeting_time"));
                booking.setBookingAddress(resultSet.getString("booking_address"));
                booking.setBookingDate(resultSet.getDate("booking_date"));
                booking.setEventId(resultSet.getInt("Event_id"));
                
                return booking;
            }
            
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Get all bookings
     * @return List of all bookings
     */
    public List<Booking> getAllBookings() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Booking> bookings = new ArrayList<>();
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM booking ORDER BY booking_date DESC";
            statement = connection.prepareStatement(query);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingId(resultSet.getInt("booking_id"));
                booking.setBookingEventId(resultSet.getInt("booking_event_id"));
                booking.setBookingName(resultSet.getString("booking_name"));
                booking.setBookingEmail(resultSet.getString("booking_email"));
                booking.setBookingPhone(resultSet.getString("booking_phone"));
                booking.setBookingSubject(resultSet.getString("booking_subject"));
                booking.setBookingMeetingTime(resultSet.getTimestamp("booking_meeting_time"));
                booking.setBookingAddress(resultSet.getString("booking_address"));
                booking.setBookingDate(resultSet.getDate("booking_date"));
                booking.setEventId(resultSet.getInt("Event_id"));
                
                bookings.add(booking);
            }
            
            return bookings;
        } catch (SQLException e) {
            e.printStackTrace();
            return bookings; // Return empty list on error
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Get bookings by event ID
     * @param eventId the ID of the event to get bookings for
     * @return List of bookings for the specified event
     */
    public List<Booking> getBookingsByEventId(int eventId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Booking> bookings = new ArrayList<>();
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM booking WHERE Event_id = ? ORDER BY booking_date DESC";
            statement = connection.prepareStatement(query);
            statement.setInt(1, eventId);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingId(resultSet.getInt("booking_id"));
                booking.setBookingEventId(resultSet.getInt("booking_event_id"));
                booking.setBookingName(resultSet.getString("booking_name"));
                booking.setBookingEmail(resultSet.getString("booking_email"));
                booking.setBookingPhone(resultSet.getString("booking_phone"));
                booking.setBookingSubject(resultSet.getString("booking_subject"));
                booking.setBookingMeetingTime(resultSet.getTimestamp("booking_meeting_time"));
                booking.setBookingAddress(resultSet.getString("booking_address"));
                booking.setBookingDate(resultSet.getDate("booking_date"));
                booking.setEventId(resultSet.getInt("Event_id"));
                
                bookings.add(booking);
            }
            
            return bookings;
        } catch (SQLException e) {
            e.printStackTrace();
            return bookings; // Return empty list on error
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Update a booking in the database
     * @param booking Booking object with updated values
     * @return true if update was successful, false otherwise
     */
    public boolean updateBooking(Booking booking) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "UPDATE booking SET booking_name = ?, booking_email = ?, booking_phone = ?, booking_subject = ?, booking_meeting_time = ?, booking_address = ? WHERE booking_id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, booking.getBookingName());
            statement.setString(2, booking.getBookingEmail());
            statement.setString(3, booking.getBookingPhone());
            statement.setString(4, booking.getBookingSubject());
            statement.setTimestamp(5, booking.getBookingMeetingTime());
            statement.setString(6, booking.getBookingAddress());
            statement.setInt(7, booking.getBookingId());
            
            int affectedRows = statement.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Delete a booking from the database
     * @param bookingId the ID of the booking to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteBooking(int bookingId) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "DELETE FROM booking WHERE booking_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, bookingId);
            
            int affectedRows = statement.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Count total bookings
     * @return total number of bookings
     */
    public int countTotalBookings() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT COUNT(*) FROM booking";
            statement = connection.prepareStatement(query);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Count bookings for a specific event
     * @param eventId the ID of the event
     * @return number of bookings for the event
     */
    public int countBookingsByEvent(int eventId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT COUNT(*) FROM booking WHERE Event_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, eventId);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
