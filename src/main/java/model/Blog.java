package model;

import java.sql.Timestamp;

public class Blog {
    private int blogId;
    private String blogTitle;
    private String blogDescription;
    private Timestamp blogPostDate;
    private int eventId; // Associated event (optional)

    // Default constructor
    public Blog() {
    }

    // Constructor without ID (for creating new blogs)
    public Blog(String blogTitle, String blogDescription, Timestamp blogPostDate, int eventId) {
        this.blogTitle = blogTitle;
        this.blogDescription = blogDescription;
        this.blogPostDate = blogPostDate;
        this.eventId = eventId;
    }

    // Full constructor with id
    public Blog(int blogId, String blogTitle, String blogDescription, Timestamp blogPostDate, int eventId) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.blogDescription = blogDescription;
        this.blogPostDate = blogPostDate;
        this.eventId = eventId;
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

    @Override
    public String toString() {
        return "Blog{" +
                "blogId=" + blogId +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogDescription='" + blogDescription + '\'' +
                ", blogPostDate=" + blogPostDate +
                ", eventId=" + eventId +
                '}';
    }
}
