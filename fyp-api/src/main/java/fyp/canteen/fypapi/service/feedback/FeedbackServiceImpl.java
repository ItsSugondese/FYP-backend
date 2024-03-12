package fyp.canteen.fypapi.service.feedback;

import fyp.canteen.fypapi.mapper.feedback.FeedbackDetailMapper;
import fyp.canteen.fypapi.repository.feedback.FeedbackRepo;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.feedback.Feedback;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.pojo.feedback.*;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import fyp.canteen.fypcore.utils.UserDataConfig;
import fyp.canteen.fypcore.utils.data.DateRangeHolder;
import fyp.canteen.fypcore.utils.data.DateTypeEnum;
import fyp.canteen.fypcore.utils.data.FromToDateGenerator;
import fyp.canteen.fypcore.utils.pagination.CustomPaginationHandler;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService{
    private final FeedbackRepo feedbackRepo;
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
    private final UserDataConfig userDataConfig;
    private final CustomPaginationHandler customPaginationHandler;
    private final FeedbackDetailMapper feedbackDetailMapper;

    @Override
    public void saveFeedback(FeedbackRequestPojo requestPojo) {
        Feedback feedback = new Feedback();

        if(requestPojo.getId() != null)
            feedback = feedbackRepo.findById(requestPojo.getId()).orElse(feedback);

        try{
            beanUtilsBean.copyProperties(feedback, requestPojo);
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }

        feedback.setFoodMenu(FoodMenu.builder().id(requestPojo.getFoodId()).build());

            feedback.setAnon(requestPojo.getIsAnonymous());
            feedback.setUser(User.builder().id(userDataConfig.userId()).build());

        feedbackRepo.save(feedback);
    }

    @Override
    public PaginationResponse getFeedbackDataPaginated(FeedbackPaginationRequest paginationRequest) {
        FromToDateGenerator.getFromToDate(DateTypeEnum.DAY, 1, paginationRequest);
        return customPaginationHandler.getPaginatedData(feedbackRepo.getAllFeedbackPaginated(paginationRequest.getFoodId(),
                paginationRequest.getFromDate(), paginationRequest.getToDate(), paginationRequest.getPageable()));
    }

    @Override
    public List<FoodMenuToFeedbackResponsePojo> getAllFoodAvaiableForFeedbacksList() {
        return feedbackDetailMapper.getAllListOfMenuToFeedback(userDataConfig.userId());
    }

    @Override
    public FeedbackStatisticsResponsePojo getFeedbackDataDetails(FeedbackStatisticsRequestPojo dateRangeHolder) {
        FromToDateGenerator.getFromToDate(DateTypeEnum.DAY, 1, dateRangeHolder);
        return feedbackDetailMapper.getFeedbackStatistics(dateRangeHolder.getFoodId(), dateRangeHolder.getFromDate(), dateRangeHolder.getToDate());
    }

    @Override
    public FeedbackRequestPojo getFeedbackGivenOnFoodByUserToday(Long foodId) {
        return feedbackDetailMapper.userOnFoodFeedbackToday(userDataConfig.userId(), foodId);
    }

    @Override
    public void deleteFeedbackById(Long feedbackId) {
         feedbackRepo.deleteById(feedbackId);
    }
}
