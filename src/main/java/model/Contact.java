package model;

import java.util.Date;

public class Contact {
    private int contactId;
    private String contactName;
    private String contactEmail;
    private String contactSubject;
    private String contactMessage;
    private Date contactDate;
    private int userId;

    // Additional field for display purposes
    private String username;

    public Contact() {
    }

    public Contact(int contactId, String contactName, String contactEmail,
                   String contactSubject, String contactMessage, Date contactDate, int userId) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactSubject = contactSubject;
        this.contactMessage = contactMessage;
        this.contactDate = contactDate;
        this.userId = userId;
    }

    // Constructor without contactId for new contact creation
    public Contact(String contactName, String contactEmail,
                   String contactSubject, String contactMessage, Date contactDate, int userId) {
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactSubject = contactSubject;
        this.contactMessage = contactMessage;
        this.contactDate = contactDate;
        this.userId = userId;
    }

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

    public Date getContactDate() {
        return contactDate;
    }

    public void setContactDate(Date contactDate) {
        this.contactDate = contactDate;
    }

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
