package dao;

import model.Feedback;
import utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Feedback entity
 */
public class FeedbackDAO {

    /**
     * Add a new feedback to the database
     * @param feedback Feedback object to be added
     * @return the ID of the newly added feedback, or -1 if operation failed
     */
    public int addFeedback(Feedback feedback) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "INSERT INTO feedback (feedback_event_id, feedback_rating, feedback_comment, feedback_date, Event_id) VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, feedback.getFeedbackEventId());
            statement.setInt(2, feedback.getFeedbackRating());
            statement.setString(3, feedback.getFeedbackComment());
            statement.setTimestamp(4, feedback.getFeedbackDate());
            statement.setInt(5, feedback.getEventId());
            
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
     * Get a feedback by ID
     * @param feedbackId the ID of the feedback to retrieve
     * @return Feedback object if found, null otherwise
     */
    public Feedback getFeedbackById(int feedbackId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM feedback WHERE feedback_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, feedbackId);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                Feedback feedback = new Feedback();
                feedback.setFeedbackId(resultSet.getInt("feedback_id"));
                feedback.setFeedbackEventId(resultSet.getInt("feedback_event_id"));
                feedback.setFeedbackRating(resultSet.getInt("feedback_rating"));
                feedback.setFeedbackComment(resultSet.getString("feedback_comment"));
                feedback.setFeedbackDate(resultSet.getTimestamp("feedback_date"));
                feedback.setEventId(resultSet.getInt("Event_id"));
                
                return feedback;
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
     * Get all feedback
     * @return List of all feedback
     */
    public List<Feedback> getAllFeedback() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Feedback> feedbackList = new ArrayList<>();
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM feedback ORDER BY feedback_date DESC";
            statement = connection.prepareStatement(query);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Feedback feedback = new Feedback();
                feedback.setFeedbackId(resultSet.getInt("feedback_id"));
                feedback.setFeedbackEventId(resultSet.getInt("feedback_event_id"));
                feedback.setFeedbackRating(resultSet.getInt("feedback_rating"));
                feedback.setFeedbackComment(resultSet.getString("feedback_comment"));
                feedback.setFeedbackDate(resultSet.getTimestamp("feedback_date"));
                feedback.setEventId(resultSet.getInt("Event_id"));
                
                feedbackList.add(feedback);
            }
            
            return feedbackList;
        } catch (SQLException e) {
            e.printStackTrace();
            return feedbackList; // Return empty list on error
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
     * Get feedback by event ID
     * @param eventId the ID of the event to get feedback for
     * @return List of feedback for the specified event
     */
    public List<Feedback> getFeedbackByEventId(int eventId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Feedback> feedbackList = new ArrayList<>();
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM feedback WHERE Event_id = ? ORDER BY feedback_date DESC";
            statement = connection.prepareStatement(query);
            statement.setInt(1, eventId);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Feedback feedback = new Feedback();
                feedback.setFeedbackId(resultSet.getInt("feedback_id"));
                feedback.setFeedbackEventId(resultSet.getInt("feedback_event_id"));
                feedback.setFeedbackRating(resultSet.getInt("feedback_rating"));
                feedback.setFeedbackComment(resultSet.getString("feedback_comment"));
                feedback.setFeedbackDate(resultSet.getTimestamp("feedback_date"));
                feedback.setEventId(resultSet.getInt("Event_id"));
                
                feedbackList.add(feedback);
            }
            
            return feedbackList;
        } catch (SQLException e) {
            e.printStackTrace();
            return feedbackList; // Return empty list on error
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
     * Update feedback in the database
     * @param feedback Feedback object with updated values
     * @return true if update was successful, false otherwise
     */
    public boolean updateFeedback(Feedback feedback) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "UPDATE feedback SET feedback_rating = ?, feedback_comment = ? WHERE feedback_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, feedback.getFeedbackRating());
            statement.setString(2, feedback.getFeedbackComment());
            statement.setInt(3, feedback.getFeedbackId());
            
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
     * Delete feedback from the database
     * @param feedbackId the ID of the feedback to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteFeedback(int feedbackId) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "DELETE FROM feedback WHERE feedback_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, feedbackId);
            
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
     * Calculate average rating for an event
     * @param eventId the ID of the event
     * @return average rating (0-5), or 0 if no ratings
     */
    public double getAverageRatingForEvent(int eventId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT AVG(feedback_rating) as avg_rating FROM feedback WHERE Event_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, eventId);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getDouble("avg_rating");
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
     * Count total feedback
     * @return total number of feedback entries
     */
    public int countTotalFeedback() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT COUNT(*) FROM feedback";
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
     * Count feedback for a specific event
     * @param eventId the ID of the event
     * @return number of feedback entries for the event
     */
    public int countFeedbackByEvent(int eventId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT COUNT(*) FROM feedback WHERE Event_id = ?";
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
