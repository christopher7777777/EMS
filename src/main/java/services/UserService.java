package services;

import dao.UserDAO;
import model.User;

import java.sql.SQLException;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public void registerUser(User user) throws SQLException {
        userDAO.addUser(user);
    }

    public User loginUser(String email, String password) throws SQLException {
        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void updateUserProfile(User user) throws SQLException {
        userDAO.updateUser(user);
    }
}