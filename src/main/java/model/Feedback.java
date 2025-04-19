package model;

import java.sql.Timestamp;

public class Feedback {
    private int feedbackId;
    private int feedbackEventId;
    private int feedbackRating;
    private String feedbackComment;
    private Timestamp feedbackDate;
    private int eventId; // Associated event

    // Default constructor
    public Feedback() {
    }

    // Constructor without ID (for creating new feedback)
    public Feedback(int feedbackEventId, int feedbackRating, String feedbackComment, Timestamp feedbackDate, int eventId) {
        this.feedbackEventId = feedbackEventId;
        this.feedbackRating = feedbackRating;
        this.feedbackComment = feedbackComment;
        this.feedbackDate = feedbackDate;
        this.eventId = eventId;
    }

    // Full constructor
    public Feedback(int feedbackId, int feedbackEventId, int feedbackRating, String feedbackComment, Timestamp feedbackDate, int eventId) {
        this.feedbackId = feedbackId;
        this.feedbackEventId = feedbackEventId;
        this.feedbackRating = feedbackRating;
        this.feedbackComment = feedbackComment;
        this.feedbackDate = feedbackDate;
        this.eventId = eventId;
    }

    // Getters and setters
    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getFeedbackEventId() {
        return feedbackEventId;
    }

    public void setFeedbackEventId(int feedbackEventId) {
        this.feedbackEventId = feedbackEventId;
    }

    public int getFeedbackRating() {
        return feedbackRating;
    }

    public void setFeedbackRating(int feedbackRating) {
        this.feedbackRating = feedbackRating;
    }

    public String getFeedbackComment() {
        return feedbackComment;
    }

    public void setFeedbackComment(String feedbackComment) {
        this.feedbackComment = feedbackComment;
    }

    public Timestamp getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Timestamp feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", feedbackEventId=" + feedbackEventId +
                ", feedbackRating=" + feedbackRating +
                ", feedbackComment='" + feedbackComment + '\'' +
                ", feedbackDate=" + feedbackDate +
                ", eventId=" + eventId +
                '}';
    }
}
