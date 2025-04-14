package model;

import java.util.Date;

public class Blog {
    private int blogId;
    private String blogTitle;
    private String blogDescription;
    private Date blogPostDate;
    private int eventId;

    // Getters and Setters
    public int getBlogId() { return blogId; }
    public void setBlogId(int blogId) { this.blogId = blogId; }
    public String getBlogTitle() { return blogTitle; }
    public void setBlogTitle(String blogTitle) { this.blogTitle = blogTitle; }
    public String getBlogDescription() { return blogDescription; }
    public void setBlogDescription(String blogDescription) { this.blogDescription = blogDescription; }
    public Date getBlogPostDate() { return blogPostDate; }
    public void setBlogPostDate(Date blogPostDate) { this.blogPostDate = blogPostDate; }
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
}