package service;

import dao.FeedbackDAO;
import model.Feedback;
import java.util.List;

public class FeedbackService {

    private FeedbackDAO feedbackDAO;

    public FeedbackService() {
        this.feedbackDAO = new FeedbackDAO();
    }

    public boolean submitFeedback(int customerId, int rating, String comments) {
        if (customerId <= 0 || rating < 1 || rating > 5) {
            return false;
        }
        Feedback feedback = new Feedback(0, customerId, rating, comments, null);
        return feedbackDAO.addFeedback(feedback);
    }

    public List<Feedback> viewAllFeedback() {
        return feedbackDAO.getAllFeedback();
    }
}