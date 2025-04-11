package model;

public class EventBookHistory {
    private int id;
    private int bookingId;
    private String action; // "Booked" or "Canceled"
    private String timestamp;

    public EventBookHistory() {}
    public EventBookHistory(int id, int bookingId, String action, String timestamp) {
        this.id = id;
        this.bookingId = bookingId;
        this.action = action;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getBookingId() {
        return bookingId;
    }
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
