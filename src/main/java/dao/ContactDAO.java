package dao;

import model.Contact;
import utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Contact entity
 */
public class ContactDAO {

    /**
     * Add a new contact message to the database
     * @param contact Contact object to be added
     * @return the ID of the newly added contact, or -1 if operation failed
     */
    public int addContact(Contact contact) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "INSERT INTO contact (contact_name, contact_email, contact_subject, contact_message, contact_date, user_id) VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, contact.getContactName());
            statement.setString(2, contact.getContactEmail());
            statement.setString(3, contact.getContactSubject());
            statement.setString(4, contact.getContactMessage());
            statement.setTimestamp(5, contact.getContactDate());
            
            // Handle nullable user_id
            if (contact.getUserId() != null) {
                statement.setInt(6, contact.getUserId());
            } else {
                statement.setNull(6, Types.INTEGER);
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
     * Get a contact by ID
     * @param contactId the ID of the contact to retrieve
     * @return Contact object if found, null otherwise
     */
    public Contact getContactById(int contactId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM contact WHERE contact_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, contactId);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                Contact contact = new Contact();
                contact.setContactId(resultSet.getInt("contact_id"));
                contact.setContactName(resultSet.getString("contact_name"));
                contact.setContactEmail(resultSet.getString("contact_email"));
                contact.setContactSubject(resultSet.getString("contact_subject"));
                contact.setContactMessage(resultSet.getString("contact_message"));
                contact.setContactDate(resultSet.getTimestamp("contact_date"));
                
                // Handle nullable user_id
                int userId = resultSet.getInt("user_id");
                if (!resultSet.wasNull()) {
                    contact.setUserId(userId);
                }
                
                return contact;
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
     * Get all contacts
     * @return List of all contacts
     */
    public List<Contact> getAllContacts() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Contact> contacts = new ArrayList<>();
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM contact ORDER BY contact_date DESC";
            statement = connection.prepareStatement(query);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setContactId(resultSet.getInt("contact_id"));
                contact.setContactName(resultSet.getString("contact_name"));
                contact.setContactEmail(resultSet.getString("contact_email"));
                contact.setContactSubject(resultSet.getString("contact_subject"));
                contact.setContactMessage(resultSet.getString("contact_message"));
                contact.setContactDate(resultSet.getTimestamp("contact_date"));
                
                // Handle nullable user_id
                int userId = resultSet.getInt("user_id");
                if (!resultSet.wasNull()) {
                    contact.setUserId(userId);
                }
                
                contacts.add(contact);
            }
            
            return contacts;
        } catch (SQLException e) {
            e.printStackTrace();
            return contacts; // Return empty list on error
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
     * Get contacts by user ID
     * @param userId the ID of the user to get contacts for
     * @return List of contacts from the specified user
     */
    public List<Contact> getContactsByUserId(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Contact> contacts = new ArrayList<>();
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM contact WHERE user_id = ? ORDER BY contact_date DESC";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setContactId(resultSet.getInt("contact_id"));
                contact.setContactName(resultSet.getString("contact_name"));
                contact.setContactEmail(resultSet.getString("contact_email"));
                contact.setContactSubject(resultSet.getString("contact_subject"));
                contact.setContactMessage(resultSet.getString("contact_message"));
                contact.setContactDate(resultSet.getTimestamp("contact_date"));
                contact.setUserId(resultSet.getInt("user_id"));
                
                contacts.add(contact);
            }
            
            return contacts;
        } catch (SQLException e) {
            e.printStackTrace();
            return contacts; // Return empty list on error
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
     * Delete a contact from the database
     * @param contactId the ID of the contact to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteContact(int contactId) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "DELETE FROM contact WHERE contact_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, contactId);
            
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
     * Count total contact messages
     * @return total number of contact messages
     */
    public int countTotalContacts() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT COUNT(*) FROM contact";
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
     * Count unread contact messages
     * This is a placeholder since we don't have a read status field in the table
     * @return number of unread contact messages
     */
    public int countUnreadContacts() {
        // This is a placeholder. In a real implementation, we would have
        // a 'read' column in the contact table to track read status.
        return 0;
    }
}
