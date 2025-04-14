package model;

import java.util.Date;

public class Booking {
    private int bookingId;
    private String bookingName;
    private String bookingEmail;
    private String bookingPhone;
    private String bookingSubject;
    private Date bookingMeetingTime;
    private String bookingAddress;
    private Date bookingDate;
    private int eventId;

    // Additional fields for display purposes
    private String eventTitle;
    private String username;

    public Booking() {
    }

    public Booking(int bookingId, String bookingName, String bookingEmail, String bookingPhone,
                   String bookingSubject, Date bookingMeetingTime, String bookingAddress,
                   Date bookingDate, int eventId) {
        this.bookingId = bookingId;
        this.bookingName = bookingName;
        this.bookingEmail = bookingEmail;
        this.bookingPhone = bookingPhone;
        this.bookingSubject = bookingSubject;
        this.bookingMeetingTime = bookingMeetingTime;
        this.bookingAddress = bookingAddress;
        this.bookingDate = bookingDate;
        this.eventId = eventId;
    }

    // Constructor without bookingId for new booking creation
    public Booking(String bookingName, String bookingEmail, String bookingPhone,
                   String bookingSubject, Date bookingMeetingTime, String bookingAddress,
                   Date bookingDate, int eventId ) {
        this.bookingName = bookingName;
        this.bookingEmail = bookingEmail;
        this.bookingPhone = bookingPhone;
        this.bookingSubject = bookingSubject;
        this.bookingMeetingTime = bookingMeetingTime;
        this.bookingAddress = bookingAddress;
        this.bookingDate = bookingDate;
        this.eventId = eventId;

    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
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

    public Date getBookingMeetingTime() {
        return bookingMeetingTime;
    }

    public void setBookingMeetingTime(Date bookingMeetingTime) {
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


    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
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
