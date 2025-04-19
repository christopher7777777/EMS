package model;

import java.sql.Timestamp;

public class Contact {
    private int contactId;
    private String contactName;
    private String contactEmail;
    private String contactSubject;
    private String contactMessage;
    private Timestamp contactDate;
    private Integer userId; // Optional user ID (nullable for anonymous messages)

    // Default constructor
    public Contact() {
    }

    // Constructor without ID (for creating new contacts)
    public Contact(String contactName, String contactEmail, String contactSubject, String contactMessage, Timestamp contactDate, Integer userId) {
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactSubject = contactSubject;
        this.contactMessage = contactMessage;
        this.contactDate = contactDate;
        this.userId = userId;
    }

    // Full constructor
    public Contact(int contactId, String contactName, String contactEmail, String contactSubject, String contactMessage, Timestamp contactDate, Integer userId) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactSubject = contactSubject;
        this.contactMessage = contactMessage;
        this.contactDate = contactDate;
        this.userId = userId;
    }

    // Getters and setters
    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactSubject() {
        return contactSubject;
    }

    public void setContactSubject(String contactSubject) {
        this.contactSubject = contactSubject;
    }

    public String getContactMessage() {
        return contactMessage;
    }

    public void setContactMessage(String contactMessage) {
        this.contactMessage = contactMessage;
    }

    public Timestamp getContactDate() {
        return contactDate;
    }

    public void setContactDate(Timestamp contactDate) {
        this.contactDate = contactDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", contactName='" + contactName + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactSubject='" + contactSubject + '\'' +
                ", contactMessage='" + contactMessage + '\'' +
                ", contactDate=" + contactDate +
                ", userId=" + userId +
                '}';
    }
}
