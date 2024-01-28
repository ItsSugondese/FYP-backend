package fyp.canteen.fypapi.service.feedback;

import fyp.canteen.fypcore.pojo.feedback.FeedbackRequestPojo;

public interface FeedbackService {
    void saveFeedback(FeedbackRequestPojo feedbackRequestPojo);
}
