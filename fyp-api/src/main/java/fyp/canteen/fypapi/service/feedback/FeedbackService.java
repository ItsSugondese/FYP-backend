package fyp.canteen.fypapi.service.feedback;

import fyp.canteen.fypcore.pojo.feedback.FeedbackPaginationRequest;
import fyp.canteen.fypcore.pojo.feedback.FeedbackRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;

public interface FeedbackService {
    void saveFeedback(FeedbackRequestPojo feedbackRequestPojo);

    PaginationResponse getFeedbackDataPaginated(FeedbackPaginationRequest paginationRequest);
}
