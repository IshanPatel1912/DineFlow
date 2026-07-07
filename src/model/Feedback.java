package model;

import java.sql.Timestamp;

public class Feedback {
    private int feedbackId;
    private int customerId;
    private int rating;
    private String comments;
    private Timestamp feedbackDate;

    public Feedback() {}

    public Feedback(int feedbackId, int customerId, int rating, String comments, Timestamp feedbackDate) {
        this.feedbackId = feedbackId;
        this.customerId = customerId;
        this.rating = rating;
        this.comments = comments;
        this.feedbackDate = feedbackDate;
    }

    public int getFeedbackId() { return feedbackId; }
    public void setFeedbackId(int feedbackId) { this.feedbackId = feedbackId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public Timestamp getFeedbackDate() { return feedbackDate; }
    public void setFeedbackDate(Timestamp feedbackDate) { this.feedbackDate = feedbackDate; }
}