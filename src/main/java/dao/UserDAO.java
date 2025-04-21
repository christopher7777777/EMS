package dao;

import model.User;
import utils.DatabaseUtil;
import utils.PasswordHash;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for User entity
 */
public class UserDAO {

    /**
     * Add a new user to the database
     * @param user User object to be added
     * @return the ID of the newly added user, or -1 if operation failed
     */
    public int addUser(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            
            // Hash the password before storing
            String hashedPassword = PasswordHash.hashPasswordWithNewSalt(user.getPassword());
            
            String query = "INSERT INTO users (username, password, email, role, profile_picture, is_active) VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
            statement.setString(2, hashedPassword);
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRole().toString());
            statement.setBytes(5, user.getProfilePicture());
            statement.setBoolean(6, user.isActive());
            
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
     * Get a user by ID
     * @param userId the ID of the user to retrieve
     * @return User object if found, null otherwise
     */
    public User getUserById(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM users WHERE users_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("users_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password")); // Note: This is the hashed password
                user.setEmail(resultSet.getString("email"));
                user.setRole(User.Role.valueOf(resultSet.getString("role")));
                user.setProfilePicture(resultSet.getBytes("profile_picture"));
                user.setActive(resultSet.getBoolean("is_active"));
                
                return user;
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
     * Get a user by username
     * @param username the username of the user to retrieve
     * @return User object if found, null otherwise
     */
    public User getUserByUsername(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM users WHERE username = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("users_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password")); // Note: This is the hashed password
                user.setEmail(resultSet.getString("email"));
                user.setRole(User.Role.valueOf(resultSet.getString("role")));
                user.setProfilePicture(resultSet.getBytes("profile_picture"));
                user.setActive(resultSet.getBoolean("is_active"));
                
                return user;
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
     * Get all users
     * @return List of all users
     */
    public List<User> getAllUsers() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM users";
            statement = connection.prepareStatement(query);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("users_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(User.Role.valueOf(resultSet.getString("role")));
                user.setProfilePicture(resultSet.getBytes("profile_picture"));
                user.setActive(resultSet.getBoolean("is_active"));
                
                users.add(user);
            }
            
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return users; // Return empty list on error
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
     * Update a user in the database
     * @param user User object with updated values
     * @return true if update was successful, false otherwise
     */
    public boolean updateUser(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query;
            
            // If password is to be updated, include it in the query
            if (user.getPassword() != null && !user.getPassword().startsWith("SHA-256:")) {
                // Password is in plaintext, hash it
                String hashedPassword = PasswordHash.hashPasswordWithNewSalt(user.getPassword());
                
                query = "UPDATE users SET username = ?, password = ?, email = ?, role = ?, profile_picture = ?, is_active = ? WHERE users_id = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, user.getUsername());
                statement.setString(2, hashedPassword);
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getRole().toString());
                statement.setBytes(5, user.getProfilePicture());
                statement.setBoolean(6, user.isActive());
                statement.setInt(7, user.getUserId());
            } else {
                // Don't update password
                query = "UPDATE users SET username = ?, email = ?, role = ?, profile_picture = ?, is_active = ? WHERE users_id = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getRole().toString());
                statement.setBytes(4, user.getProfilePicture());
                statement.setBoolean(5, user.isActive());
                statement.setInt(6, user.getUserId());
            }
            
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
     * Delete a user from the database
     * @param userId the ID of the user to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteUser(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "DELETE FROM users WHERE users_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            
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
     * Toggle the active status of a user
     * @param userId the ID of the user to toggle
     * @param isActive the new active status
     * @return true if update was successful, false otherwise
     */
    public boolean toggleUserActiveStatus(int userId, boolean isActive) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "UPDATE users SET is_active = ? WHERE users_id = ?";
            statement = connection.prepareStatement(query);
            statement.setBoolean(1, isActive);
            statement.setInt(2, userId);
            
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
     * Authenticate a user
     * @param username the username of the user
     * @param password the password of the user (plaintext)
     * @return User object if authentication was successful, null otherwise
     */
    public User authenticateUser(String username, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM users WHERE username = ? AND is_active = TRUE";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                // Verify password
                String storedPassword = resultSet.getString("password");
                if (PasswordHash.verifyPassword(password, storedPassword)) {
                    User user = new User();
                    user.setUserId(resultSet.getInt("users_id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(storedPassword); // Store the hashed password
                    user.setEmail(resultSet.getString("email"));
                    user.setRole(User.Role.valueOf(resultSet.getString("role")));
                    user.setProfilePicture(resultSet.getBytes("profile_picture"));
                    user.setActive(resultSet.getBoolean("is_active"));
                    
                    return user;
                }
            }
            
            return null; // Authentication failed
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
     * Check if a username already exists
     * @param username the username to check
     * @return true if username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT COUNT(*) FROM users WHERE username = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
     * Check if an email already exists
     * @param email the email to check
     * @return true if email exists, false otherwise
     */
    public boolean emailExists(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT COUNT(*) FROM users WHERE email = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
     * Count total users
     * @return total number of users
     */
    public int countTotalUsers() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT COUNT(*) FROM users";
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
     * Count active users
     * @return number of active users
     */
    public int countActiveUsers() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT COUNT(*) FROM users WHERE is_active = TRUE";
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
     * Count users by role
     * @param role the role to count
     * @return number of users with the specified role
     */
    public int countUsersByRole(User.Role role) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT COUNT(*) FROM users WHERE role = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, role.toString());
            
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
