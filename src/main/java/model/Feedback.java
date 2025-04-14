package model;

import java.util.Date;

public class Feedback {
    private int feedbackId;
    private int eventId;
    private int feedbackRating;
    private String feedbackComment;
    private Date feedbackDate;

    // Getters and Setters
    public int getFeedbackId() { return feedbackId; }
    public void setFeedbackId(int feedbackId) { this.feedbackId = feedbackId; }
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public int getFeedbackRating() { return feedbackRating; }
    public void setFeedbackRating(int feedbackRating) { this.feedbackRating = feedbackRating; }
    public String getFeedbackComment() { return feedbackComment; }
    public void setFeedbackComment(String feedbackComment) { this.feedbackComment = feedbackComment; }
    public Date getFeedbackDate() { return feedbackDate; }
    public void setFeedbackDate(Date feedbackDate) { this.feedbackDate = feedbackDate; }
}