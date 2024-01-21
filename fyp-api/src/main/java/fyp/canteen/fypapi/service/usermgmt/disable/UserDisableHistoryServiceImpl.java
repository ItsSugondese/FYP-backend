package fyp.canteen.fypapi.service.usermgmt.disable;

import fyp.canteen.fypapi.repository.usermgmt.UserDisableHistoryRepo;
import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypapi.service.usermgmt.UserService;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.model.entity.usermgmt.UserDisableHistory;
import fyp.canteen.fypcore.pojo.usermgmt.UserDetailPaginationRequest;
import fyp.canteen.fypcore.pojo.usermgmt.disable.UserDisableHistoryPaginationRequest;
import fyp.canteen.fypcore.pojo.usermgmt.disable.UserDisableHistoryRequestPojo;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import fyp.canteen.fypcore.utils.pagination.CustomPaginationHandler;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDisableHistoryServiceImpl implements UserDisableHistoryService{
    private final UserDisableHistoryRepo userDisableHistoryRepo;
    private final UserService userService;
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
    private final CustomPaginationHandler customPaginationHandler;
    private final UserRepo userRepo;
    @Override
    @Transactional
    public void saveUserDisableHistory(UserDisableHistoryRequestPojo requestPojo) {
        User user = userService.findUserById(requestPojo.getUserId());

        UserDisableHistory userDisableHistory = new UserDisableHistory();

        savedValidation(requestPojo, user);

        try{
            beanUtilsBean.copyProperties(userDisableHistory, requestPojo);
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }

        userDisableHistory.setUser(user);
        user.setAccountNonLocked(!requestPojo.getIsDisabled());
        userRepo.save(user);
        userDisableHistoryRepo.save(userDisableHistory);
    }

    @Override
    public PaginationResponse getDisableHistoryPaginated(UserDisableHistoryPaginationRequest paginationRequest) {
        if(paginationRequest.getUserId() == null){
            throw new AppException("Providing user id is must");
        }
        return customPaginationHandler.getPaginatedData(userDisableHistoryRepo.getDisableHistoryPaginated(paginationRequest.getUserId(), paginationRequest.getPageable()));
    }

    private void savedValidation(UserDisableHistoryRequestPojo requestPojo, User user) {
        if(Boolean.TRUE.equals(requestPojo.getIsDisabled()) && requestPojo.getRemarks() == null){
            throw new AppException("Providing remarks is must when disabling an account");
        }

        if(user.isAccountNonLocked() == !requestPojo.getIsDisabled())
            throw new AppException("The user is already " + (user.isAccountNonLocked()? "Running" : "Disabled"));

    }
}
