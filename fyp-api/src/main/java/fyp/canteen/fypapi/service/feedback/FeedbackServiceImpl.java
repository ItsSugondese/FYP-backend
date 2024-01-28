package fyp.canteen.fypapi.service.feedback;

import fyp.canteen.fypapi.repository.feedback.FeedbackRepo;
import fyp.canteen.fypapi.service.food.FoodMenuService;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.feedback.Feedback;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.pojo.feedback.FeedbackRequestPojo;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import fyp.canteen.fypcore.utils.UserDataConfig;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService{
    private final FeedbackRepo feedbackRepo;
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
    private final UserDataConfig userDataConfig;
    private final FoodMenuService foodMenuService;

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

        if(!requestPojo.getIsAnonymous())
            feedback.setUser(User.builder().id(userDataConfig.userId()).build());

        feedbackRepo.save(feedback);
    }
}
