package fyp.canteen.fypapi.service.feedback;

import fyp.canteen.fypcore.pojo.feedback.*;
import fyp.canteen.fypcore.utils.data.DateRangeHolder;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;

import java.time.LocalDate;
import java.util.List;

public interface FeedbackService {
    void saveFeedback(FeedbackRequestPojo feedbackRequestPojo);

    PaginationResponse getFeedbackDataPaginated(FeedbackPaginationRequest paginationRequest);
    FeedbackStatisticsResponsePojo getFeedbackDataDetails(FeedbackStatisticsRequestPojo dateRangeHolder);

    List<FoodMenuToFeedbackResponsePojo> getAllFoodAvaiableForFeedbacksList();
}
