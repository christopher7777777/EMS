package model;

import java.util.Date;

public class Blog {
    private int blogId;
    private String blogTitle;
    private String blogDescription;
    private Date blogPostDate;
    private int eventId;

    // Additional field for display purposes
    private String authorName;

    public Blog() {
    }

    public Blog(int blogId, String blogTitle, String blogDescription,
                Date blogPostDate, int eventId) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.blogDescription = blogDescription;
        this.blogPostDate = blogPostDate;
        this.eventId= eventId;
    }

    // Constructor without blogId for new blog creation
    public Blog(String blogTitle, String blogDescription,
                Date blogPostDate, int eventID) {
        this.blogTitle = blogTitle;
        this.blogDescription = blogDescription;
        this.blogPostDate = blogPostDate;
        this.eventId= eventID;
    }

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

    public Date getBlogPostDate() {
        return blogPostDate;
    }

    public void setBlogPostDate(Date blogPostDate) {
        this.blogPostDate = blogPostDate;
    }

    public int geteventId() {
        return eventId;
    }

    public void seteventId(int eventID) {
        this.eventId= eventID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "blogId=" + blogId +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogDescription='" + blogDescription + '\'' +
                ", blogPostDate=" + blogPostDate +
                ", eventID=" + eventId+
                '}';
    }
}
