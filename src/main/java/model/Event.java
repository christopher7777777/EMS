package model;

import java.sql.Date;

public class Event {
    private int eventId;
    private String eventTitle;
    private String eventDescription;
    private Date eventDate;
    private String eventLocation;
    private double eventPrice;
    private int userId; // Creator of the event (admin)
    private byte[] eventImage; // Store image as byte array

    // Default constructor
    public Event() {
    }

    // Constructor without ID (for creating new events)
    public Event(String eventTitle, String eventDescription, Date eventDate, String eventLocation, double eventPrice, int userId, byte[] eventImage) {
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventPrice = eventPrice;
        this.userId = userId;
        this.eventImage = eventImage;
    }

    // Full constructor
    public Event(int eventId, String eventTitle, String eventDescription, Date eventDate, String eventLocation, double eventPrice, int userId, byte[] eventImage) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventPrice = eventPrice;
        this.userId = userId;
        this.eventImage = eventImage;
    }

    // Getters and setters
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public double getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(double eventPrice) {
        this.eventPrice = eventPrice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte[] getEventImage() {
        return eventImage;
    }

    public void setEventImage(byte[] eventImage) {
        this.eventImage = eventImage;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventTitle='" + eventTitle + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventDate=" + eventDate +
                ", eventLocation='" + eventLocation + '\'' +
                ", eventPrice=" + eventPrice +
                ", userId=" + userId +
                ", eventImage=" + (eventImage != null ? "Image Present" : "No Image") +
                '}';
    }
}
