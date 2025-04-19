package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Booking {
    private int bookingId;
    private int bookingEventId;
    private String bookingName;
    private String bookingEmail;
    private String bookingPhone;
    private String bookingSubject;
    private Timestamp bookingMeetingTime;
    private String bookingAddress;
    private Date bookingDate;
    private int eventId; // Associated event

    // Default constructor
    public Booking() {
    }

    // Constructor without ID (for creating new bookings)
    public Booking(int bookingEventId, String bookingName, String bookingEmail, String bookingPhone, 
                   String bookingSubject, Timestamp bookingMeetingTime, String bookingAddress, 
                   Date bookingDate, int eventId) {
        this.bookingEventId = bookingEventId;
        this.bookingName = bookingName;
        this.bookingEmail = bookingEmail;
        this.bookingPhone = bookingPhone;
        this.bookingSubject = bookingSubject;
        this.bookingMeetingTime = bookingMeetingTime;
        this.bookingAddress = bookingAddress;
        this.bookingDate = bookingDate;
        this.eventId = eventId;
    }

    // Full constructor
    public Booking(int bookingId, int bookingEventId, String bookingName, String bookingEmail, 
                   String bookingPhone, String bookingSubject, Timestamp bookingMeetingTime, 
                   String bookingAddress, Date bookingDate, int eventId) {
        this.bookingId = bookingId;
        this.bookingEventId = bookingEventId;
        this.bookingName = bookingName;
        this.bookingEmail = bookingEmail;
        this.bookingPhone = bookingPhone;
        this.bookingSubject = bookingSubject;
        this.bookingMeetingTime = bookingMeetingTime;
        this.bookingAddress = bookingAddress;
        this.bookingDate = bookingDate;
        this.eventId = eventId;
    }

    // Getters and setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getBookingEventId() {
        return bookingEventId;
    }

    public void setBookingEventId(int bookingEventId) {
        this.bookingEventId = bookingEventId;
    }

    public String getBookingName() {
        return bookingName;
    }

    public void setBookingName(String bookingName) {
        this.bookingName = bookingName;
    }

    public String getBookingEmail() {
        return bookingEmail;
    }

    public void setBookingEmail(String bookingEmail) {
        this.bookingEmail = bookingEmail;
    }

    public String getBookingPhone() {
        return bookingPhone;
    }

    public void setBookingPhone(String bookingPhone) {
        this.bookingPhone = bookingPhone;
    }

    public String getBookingSubject() {
        return bookingSubject;
    }

    public void setBookingSubject(String bookingSubject) {
        this.bookingSubject = bookingSubject;
    }

    public Timestamp getBookingMeetingTime() {
        return bookingMeetingTime;
    }

    public void setBookingMeetingTime(Timestamp bookingMeetingTime) {
        this.bookingMeetingTime = bookingMeetingTime;
    }

    public String getBookingAddress() {
        return bookingAddress;
    }

    public void setBookingAddress(String bookingAddress) {
        this.bookingAddress = bookingAddress;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", bookingEventId=" + bookingEventId +
                ", bookingName='" + bookingName + '\'' +
                ", bookingEmail='" + bookingEmail + '\'' +
                ", bookingPhone='" + bookingPhone + '\'' +
                ", bookingSubject='" + bookingSubject + '\'' +
                ", bookingMeetingTime=" + bookingMeetingTime +
                ", bookingAddress='" + bookingAddress + '\'' +
                ", bookingDate=" + bookingDate +
                ", eventId=" + eventId +
                '}';
    }
}
