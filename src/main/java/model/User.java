package model;

public class User {
    private int id;
    private String email;
    private String password;
    private String role; // "Admin" or "Customer"
    private String image; // URL or path to profile image

    public User() {}
    public User(int id, String email, String password, String role, String image) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.image = image;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}