package model;

import java.io.InputStream;

public class User {
    // User attributes
    private int userId;
    private String username;
    private String password;
    private String email;
    private Role role; // Role can be ADMIN or CUSTOMER
    private byte[] profilePicture; // User profile picture stored as byte array
    private boolean isActive; // Indicates if the user account is active

    // Enum for user roles
    public enum Role {
        ADMIN, CUSTOMER
    }

    // Default constructor
    public User() {
    }

    // Constructor for new user registration (without ID and profile picture)
    public User(String username, String password, String email, Role role, boolean isActive) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.isActive = isActive;
    }

    // Full constructor including userId and profile picture
    public User(int userId, String username, String password, String email, Role role, byte[] profilePicture, boolean isActive) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.profilePicture = profilePicture;
        this.isActive = isActive;
    }

    // Getter and setter methods

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

    // Helper method to check if user is an admin
    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    // Helper method to check if user is a customer
    public boolean isCustomer() {
        return role == Role.CUSTOMER;
    }

    // Override toString() for easier logging/debugging
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
