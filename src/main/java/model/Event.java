package model;

import java.util.Date;

public class Event {
    private int eventId;
    private String eventTitle;
    private String eventDescription;
    private Date eventDate;
    private String eventLocation;
    private double eventPrice;
    private int userId;

    // Getters and Setters
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public String getEventTitle() { return eventTitle; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }
    public String getEventDescription() { return eventDescription; }
    public void setEventDescription(String eventDescription) { this.eventDescription = eventDescription; }
    public Date getEventDate() { return eventDate; }
    public void setEventDate(Date eventDate) { this.eventDate = eventDate; }
    public String getEventLocation() { return eventLocation; }
    public void setEventLocation(String eventLocation) { this.eventLocation = eventLocation; }
    public double getEventPrice() { return eventPrice; }
    public void setEventPrice(double eventPrice) { this.eventPrice = eventPrice; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}