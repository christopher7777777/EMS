package dao;

import model.Blog;
import utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Blog entity
 */
public class BlogDAO {

    /**
     * Add a new blog to the database
     * @param blog Blog object to be added
     * @return the ID of the newly added blog
     */
    public int addBlog(Blog blog) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "INSERT INTO blog (blog_title, blog_description, blog_post_date, Event_id) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, blog.getBlogTitle());
            statement.setString(2, blog.getBlogDescription());
            statement.setTimestamp(3, blog.getBlogPostDate());
            statement.setInt(4, blog.getEventId());
            
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
     * Get a blog by ID
     * @param blogId the ID of the blog to retrieve
     * @return Blog object if found, null otherwise
     */
    public Blog getBlogById(int blogId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM blog WHERE blog_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, blogId);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                Blog blog = new Blog();
                blog.setBlogId(resultSet.getInt("blog_id"));
                blog.setBlogTitle(resultSet.getString("blog_title"));
                blog.setBlogDescription(resultSet.getString("blog_description"));
                blog.setBlogPostDate(resultSet.getTimestamp("blog_post_date"));
                blog.setEventId(resultSet.getInt("Event_id"));
                
                return blog;
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
     * Get all blogs
     * @return List of all blogs
     */
    public List<Blog> getAllBlogs() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Blog> blogs = new ArrayList<>();
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM blog ORDER BY blog_post_date DESC";
            statement = connection.prepareStatement(query);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Blog blog = new Blog();
                blog.setBlogId(resultSet.getInt("blog_id"));
                blog.setBlogTitle(resultSet.getString("blog_title"));
                blog.setBlogDescription(resultSet.getString("blog_description"));
                blog.setBlogPostDate(resultSet.getTimestamp("blog_post_date"));
                blog.setEventId(resultSet.getInt("Event_id"));
                
                blogs.add(blog);
            }
            
            return blogs;
        } catch (SQLException e) {
            e.printStackTrace();
            return blogs; // Return empty list on error
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
     * Get paginated blogs
     * @param offset the starting index
     * @param limit the number of blogs to retrieve
     * @return List of blogs
     */
    public List<Blog> getPaginatedBlogs(int offset, int limit) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Blog> blogs = new ArrayList<>();
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM blog ORDER BY blog_post_date DESC LIMIT ? OFFSET ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Blog blog = new Blog();
                blog.setBlogId(resultSet.getInt("blog_id"));
                blog.setBlogTitle(resultSet.getString("blog_title"));
                blog.setBlogDescription(resultSet.getString("blog_description"));
                blog.setBlogPostDate(resultSet.getTimestamp("blog_post_date"));
                blog.setEventId(resultSet.getInt("Event_id"));
                
                blogs.add(blog);
            }
            
            return blogs;
        } catch (SQLException e) {
            e.printStackTrace();
            return blogs; // Return empty list on error
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
     * Get blogs by event ID
     * @param eventId the ID of the event to get blogs for
     * @return List of blogs for the specified event
     */
    public List<Blog> getBlogsByEventId(int eventId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Blog> blogs = new ArrayList<>();
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM blog WHERE Event_id = ? ORDER BY blog_post_date DESC";
            statement = connection.prepareStatement(query);
            statement.setInt(1, eventId);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Blog blog = new Blog();
                blog.setBlogId(resultSet.getInt("blog_id"));
                blog.setBlogTitle(resultSet.getString("blog_title"));
                blog.setBlogDescription(resultSet.getString("blog_description"));
                blog.setBlogPostDate(resultSet.getTimestamp("blog_post_date"));
                blog.setEventId(resultSet.getInt("Event_id"));
                
                blogs.add(blog);
            }
            
            return blogs;
        } catch (SQLException e) {
            e.printStackTrace();
            return blogs; // Return empty list on error
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
     * Update a blog in the database
     * @param blog Blog object with updated values
     * @return true if update was successful, false otherwise
     */
    public boolean updateBlog(Blog blog) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "UPDATE blog SET blog_title = ?, blog_description = ? WHERE blog_id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, blog.getBlogTitle());
            statement.setString(2, blog.getBlogDescription());
            statement.setInt(3, blog.getBlogId());
            
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
     * Delete a blog from the database
     * @param blogId the ID of the blog to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteBlog(int blogId) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "DELETE FROM blog WHERE blog_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, blogId);
            
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
     * Search for blogs by title
     * @param keyword the search keyword
     * @return List of matching blogs
     */
    public List<Blog> searchBlogs(String keyword) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Blog> blogs = new ArrayList<>();
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT * FROM blog WHERE blog_title LIKE ? OR blog_description LIKE ? ORDER BY blog_post_date DESC";
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Blog blog = new Blog();
                blog.setBlogId(resultSet.getInt("blog_id"));
                blog.setBlogTitle(resultSet.getString("blog_title"));
                blog.setBlogDescription(resultSet.getString("blog_description"));
                blog.setBlogPostDate(resultSet.getTimestamp("blog_post_date"));
                blog.setEventId(resultSet.getInt("Event_id"));
                
                blogs.add(blog);
            }
            
            return blogs;
        } catch (SQLException e) {
            e.printStackTrace();
            return blogs; // Return empty list on error
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
     * Count total blogs
     * @return total number of blogs
     */
    public int countTotalBlogs() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseUtil.getConnection();
            String query = "SELECT COUNT(*) FROM blog";
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
}
