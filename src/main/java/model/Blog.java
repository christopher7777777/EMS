package model; // Package declaration 
import java.sql.Timestamp; // Importing Timestamp class to store date and time of the blog post

// This class represents a blog post with properties such as title, description, date, related event, and an image.
public class Blog {
    // Fields representing blog details
    private int blogId; // Unique identifier for the blog post
    private String blogTitle; // Title of the blog
    private String blogDescription; // Description/content of the blog
    private Timestamp blogPostDate; // Date and time when the blog was posted
    private int eventId; // ID of the related event, if any
    private byte[] blogImage; // Image associated with the blog (stored as byte array)

    // Default constructor - used when no initial values are provided
    public Blog() {
    }

    // Constructor used when creating a new blog post (blogId will be auto-generated or set later)
    public Blog(String blogTitle, String blogDescription, Timestamp blogPostDate, int eventId, byte[] blogImage) {
        this.blogTitle = blogTitle;
        this.blogDescription = blogDescription;
        this.blogPostDate = blogPostDate;
        this.eventId = eventId;
        this.blogImage = blogImage;
    }

    // Full constructor with all fields including blogId (useful when retrieving or updating an existing blog post)
    public Blog(int blogId, String blogTitle, String blogDescription, Timestamp blogPostDate, int eventId, byte[] blogImage) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.blogDescription = blogDescription;
        this.blogPostDate = blogPostDate;
        this.eventId = eventId;
        this.blogImage = blogImage;
    }

    // Getter for blogId
    public int getBlogId() {
        return blogId;
    }

    // Setter for blogId
    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    // Getter for blogTitle
    public String getBlogTitle() {
        return blogTitle;
    }

    // Setter for blogTitle
    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    // Getter for blogDescription
    public String getBlogDescription() {
        return blogDescription;
    }

    // Setter for blogDescription
    public void setBlogDescription(String blogDescription) {
        this.blogDescription = blogDescription;
    }

    // Getter for blogPostDate
    public Timestamp getBlogPostDate() {
        return blogPostDate;
    }

    // Setter for blogPostDate
    public void setBlogPostDate(Timestamp blogPostDate) {
        this.blogPostDate = blogPostDate;
    }

    // Getter for eventId
    public int getEventId() {
        return eventId;
    }

    // Setter for eventId
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    // Getter for blogImage
    public byte[] getBlogImage() {
        return blogImage;
    }

    // Setter for blogImage
    public void setBlogImage(byte[] blogImage) {
        this.blogImage = blogImage;
    }

    // Overridden toString method to display blog information as a string
    @Override
    public String toString() {
        return "Blog{" +
                "blogId=" + blogId +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogDescription='" + blogDescription + '\'' +
                ", blogPostDate=" + blogPostDate +
                ", eventId=" + eventId +
                ", blogImage=" + (blogImage != null ? "present" : "null") + // Show if image is present
                '}';
    }
}
