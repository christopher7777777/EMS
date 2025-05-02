package model;

import java.sql.Timestamp;

public class Blog {
    private int blogId;
    private String blogTitle;
    private String blogDescription;
    private Timestamp blogPostDate;
    private int eventId;
    private byte[] blogImage; // New field for image

    // Default constructor
    public Blog() {
    }

    // Constructor without ID (for creating new blogs)
    public Blog(String blogTitle, String blogDescription, Timestamp blogPostDate, int eventId, byte[] blogImage) {
        this.blogTitle = blogTitle;
        this.blogDescription = blogDescription;
        this.blogPostDate = blogPostDate;
        this.eventId = eventId;
        this.blogImage = blogImage;
    }

    // Full constructor with id
    public Blog(int blogId, String blogTitle, String blogDescription, Timestamp blogPostDate, int eventId, byte[] blogImage) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.blogDescription = blogDescription;
        this.blogPostDate = blogPostDate;
        this.eventId = eventId;
        this.blogImage = blogImage;
    }

    // Getters and setters
    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogDescription() {
        return blogDescription;
    }

    public void setBlogDescription(String blogDescription) {
        this.blogDescription = blogDescription;
    }

    public Timestamp getBlogPostDate() {
        return blogPostDate;
    }

    public void setBlogPostDate(Timestamp blogPostDate) {
        this.blogPostDate = blogPostDate;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public byte[] getBlogImage() {
        return blogImage;
    }

    public void setBlogImage(byte[] blogImage) {
        this.blogImage = blogImage;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "blogId=" + blogId +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogDescription='" + blogDescription + '\'' +
                ", blogPostDate=" + blogPostDate +
                ", eventId=" + eventId +
                ", blogImage=" + (blogImage != null ? "present" : "null") +
                '}';
    }
}