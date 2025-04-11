package model;

public class Booking {
    private int id;
    private int customerId;
    private int eventId;
    private String bookingDate;

    public Booking() {}
    public Booking(int id, int customerId, int eventId, String bookingDate) {
        this.id = id;
        this.customerId = customerId;
        this.eventId = eventId;
        this.bookingDate = bookingDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    public String getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
}
