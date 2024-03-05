package fyp.canteen.fypapi.service.notification;

import fyp.canteen.fypapi.repository.notification.NotificationRepo;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.model.notification.Notification;
import fyp.canteen.fypcore.pojo.notification.NotificationRequestPojo;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import fyp.canteen.fypcore.utils.UserDataConfig;
import fyp.canteen.fypcore.utils.pagination.CustomPaginationHandler;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepo notificationRepo;
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
    private final UserDataConfig userDataConfig;
    private final CustomPaginationHandler customPaginationHandler;
    @Override
    public void saveNotification(NotificationRequestPojo requestPojo) {
        Notification notification = new Notification();
        boolean exists;
        if(requestPojo.getId() != null)
            notification = notificationRepo.findById(requestPojo.getId()).orElse(notification);

        exists = notification.getId() != null;

        try{
            beanUtilsBean.copyProperties(notification, requestPojo);
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }

        if(!exists)
            notification.setUser(User.builder().id(requestPojo.getUserId()).build());

        notificationRepo.save(notification);
    }

    @Override
    public PaginationResponse getAllNotificationOfMember(PaginationRequest request) {
        return customPaginationHandler.getPaginatedData(
                notificationRepo.getAllNotificationMessageOfMember(userDataConfig.userId(), request.getPageable()));
    }


    @Override
    public void markAsSeen() {
        notificationRepo.updateAllMessageAsSeen(userDataConfig.userId());
    }

    @Override
    public Integer newNotifications() {
        return notificationRepo.getNewNotificationCount(userDataConfig.userId());
    }

}
