package fyp.canteen.fypapi.service.feedback;

import fyp.canteen.fypcore.pojo.feedback.FeedbackPaginationRequest;
import fyp.canteen.fypcore.pojo.feedback.FeedbackRequestPojo;
import fyp.canteen.fypcore.pojo.feedback.FeedbackStatisticsResponsePojo;
import fyp.canteen.fypcore.utils.data.DateRangeHolder;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;

import java.time.LocalDate;

public interface FeedbackService {
    void saveFeedback(FeedbackRequestPojo feedbackRequestPojo);

    PaginationResponse getFeedbackDataPaginated(FeedbackPaginationRequest paginationRequest);
    FeedbackStatisticsResponsePojo getFeedbackDataDetails(DateRangeHolder dateRangeHolder);
}
