package model;

import java.io.InputStream;

public class User {
    private int userId;
    private String username;
    private String password;
    private String email;
    private Role role;
    private byte[] profilePicture;
    private boolean isActive;

    public enum Role {
        ADMIN, CUSTOMER
    }

    // Default constructor
    public User() {
    }

    // Constructor without userId and profilePicture (for new user registration)
    public User(String username, String password, String email, Role role, boolean isActive) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.isActive = isActive;
    }

    // Full constructor
    public User(int userId, String username, String password, String email, Role role, byte[] profilePicture, boolean isActive) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.profilePicture = profilePicture;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // Check if user is admin
    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    // Check if user is customer
    public boolean isCustomer() {
        return role == Role.CUSTOMER;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                '}';
    }
}
