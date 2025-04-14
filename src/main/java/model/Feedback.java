package model;

import java.util.Date;

public class Feedback {
    private int feedbackId;
    private int feedbackRating;
    private String feedbackComment;
    private Date feedbackDate;
    private int eventId;

    // Additional fields for display purposes
    private String eventTitle;
    private String username;

    public Feedback() {
    }

    public Feedback(int feedbackId, int feedbackRating, String feedbackComment,
                    Date feedbackDate, int eventId) {
        this.feedbackId = feedbackId;
        this.feedbackRating = feedbackRating;
        this.feedbackComment = feedbackComment;
        this.feedbackDate = feedbackDate;
        this.eventId = eventId;
    }

    // Constructor without feedbackId for new feedback creation
    public Feedback(int feedbackRating, String feedbackComment,
                    Date feedbackDate, int eventId, int userId) {
        this.feedbackRating = feedbackRating;
        this.feedbackComment = feedbackComment;
        this.feedbackDate = feedbackDate;
        this.eventId = eventId;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
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

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
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
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", feedbackRating=" + feedbackRating +
                ", feedbackComment='" + feedbackComment + '\'' +
                ", feedbackDate=" + feedbackDate +
                ", eventId=" + eventId +
                '}';
    }
}
