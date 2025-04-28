package dao;

import model.Event;
import utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Event entity
 */
public class EventDAO {

    /**
     * Add a new event to the database
     * @param event Event object to be added
     * @return the ID of the newly added event, or -1 if operation failed
     */
    public int addEvent(Event event) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseUtil.getConnection();
            String query = "INSERT INTO event (event_title, event_description, event_date, event_location, event_price, User_id, event_image) VALUES (?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, event.getEventTitle());
            statement.setString(2, event.getEventDescription());
            statement.setDate(3, event.getEventDate());
            statement.setString(4, event.getEventLocation());
            statement.setDouble(5, event.getEventPrice());
            statement.setInt(6, event.getUserId());
            if (event.getEventImage() != null) {
                statement.setBytes(7, event.getEventImage());
            } else {
                statement.setNull(7, Types.BLOB);
            }

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
     * Get an event by ID
     * @param eventId the ID of the event to retrieve
     * @return Event object if found, null otherwise
     */
    public Event getEventById(int eventId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM event WHERE event_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, eventId);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Event event = new Event();
                event.setEventId(resultSet.getInt("event_id"));
                event.setEventTitle(resultSet.getString("event_title"));
                event.setEventDescription(resultSet.getString("event_description"));
                event.setEventDate(resultSet.getDate("event_date"));
                event.setEventLocation(resultSet.getString("event_location"));
                event.setEventPrice(resultSet.getDouble("event_price"));
                event.setUserId(resultSet.getInt("User_id"));
                event.setEventImage(resultSet.getBytes("event_image"));

                return event;
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
     * Get all events
     * @return List of all events
     */
    public List<Event> getAllEvents() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Event> events = new ArrayList<>();

        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM event ORDER BY event_date DESC";
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Event event = new Event();
                event.setEventId(resultSet.getInt("event_id"));
                event.setEventTitle(resultSet.getString("event_title"));
                event.setEventDescription(resultSet.getString("event_description"));
                event.setEventDate(resultSet.getDate("event_date"));
                event.setEventLocation(resultSet.getString("event_location"));
                event.setEventPrice(resultSet.getDouble("event_price"));
                event.setUserId(resultSet.getInt("User_id"));
                event.setEventImage(resultSet.getBytes("event_image"));

                events.add(event);
            }

            return events;
        } catch (SQLException e) {
            e.printStackTrace();
            return events; // Return empty list on error
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
     * Get paginated events
     * @param offset the starting index
     * @param limit the number of events to retrieve
     * @return List of events
     */
    public List<Event> getPaginatedEvents(int offset, int limit) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Event> events = new ArrayList<>();

        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM event ORDER BY event_date DESC LIMIT ? OFFSET ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, limit);
            statement.setInt(2, offset);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Event event = new Event();
                event.setEventId(resultSet.getInt("event_id"));
                event.setEventTitle(resultSet.getString("event_title"));
                event.setEventDescription(resultSet.getString("event_description"));
                event.setEventDate(resultSet.getDate("event_date"));
                event.setEventLocation(resultSet.getString("event_location"));
                event.setEventPrice(resultSet.getDouble("event_price"));
                event.setUserId(resultSet.getInt("User_id"));
                event.setEventImage(resultSet.getBytes("event_image"));

                events.add(event);
            }

            return events;
        } catch (SQLException e) {
            e.printStackTrace();
            return events; // Return empty list on error
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
     * Update an event in the database
     * @param event Event object with updated values
     * @return true if update was successful, false otherwise
     */
    public boolean updateEvent(Event event) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseUtil.getConnection();
            String query = "UPDATE event SET event_title = ?, event_description = ?, event_date = ?, event_location = ?, event_price = ?, event_image = ? WHERE event_id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, event.getEventTitle());
            statement.setString(2, event.getEventDescription());
            statement.setDate(3, event.getEventDate());
            statement.setString(4, event.getEventLocation());
            statement.setDouble(5, event.getEventPrice());
            if (event.getEventImage() != null) {
                statement.setBytes(6, event.getEventImage());
            } else {
                statement.setNull(6, Types.BLOB);
            }
            statement.setInt(7, event.getEventId());

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
     * Delete an event from the database
     * @param eventId the ID of the event to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteEvent(int eventId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseUtil.getConnection();
            String query = "DELETE FROM event WHERE event_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, eventId);

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
     * Search for events by title or location
     * @param keyword the search keyword
     * @return List of matching events
     */
    public List<Event> searchEvents(String keyword) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Event> events = new ArrayList<>();

        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM event WHERE event_title LIKE ? OR event_location LIKE ? ORDER BY event_date DESC";
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Event event = new Event();
                event.setEventId(resultSet.getInt("event_id"));
                event.setEventTitle(resultSet.getString("event_title"));
                event.setEventDescription(resultSet.getString("event_description"));
                event.setEventDate(resultSet.getDate("event_date"));
                event.setEventLocation(resultSet.getString("event_location"));
                event.setEventPrice(resultSet.getDouble("event_price"));
                event.setUserId(resultSet.getInt("User_id"));
                event.setEventImage(resultSet.getBytes("event_image"));

                events.add(event);
            }

            return events;
        } catch (SQLException e) {
            e.printStackTrace();
            return events; // Return empty list on error
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
     * Count total events
     * @return total number of events
     */
    public int countTotalEvents() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT COUNT(*) FROM event";
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
     * Get events by creator (user ID)
     * @param userId the ID of the event creator
     * @return List of events created by the specified user
     */
    public List<Event> getEventsByCreator(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Event> events = new ArrayList<>();

        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM event WHERE User_id = ? ORDER BY event_date DESC";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Event event = new Event();
                event.setEventId(resultSet.getInt("event_id"));
                event.setEventTitle(resultSet.getString("event_title"));
                event.setEventDescription(resultSet.getString("event_description"));
                event.setEventDate(resultSet.getDate("event_date"));
                event.setEventLocation(resultSet.getString("event_location"));
                event.setEventPrice(resultSet.getDouble("event_price"));
                event.setUserId(resultSet.getInt("User_id"));
                event.setEventImage(resultSet.getBytes("event_image"));

                events.add(event);
            }

            return events;
        } catch (SQLException e) {
            e.printStackTrace();
            return events; // Return empty list on error
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