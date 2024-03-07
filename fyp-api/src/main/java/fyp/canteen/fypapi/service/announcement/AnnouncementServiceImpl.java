package fyp.canteen.fypapi.service.announcement;

import fyp.canteen.fypapi.repository.auth.AnnouncementRepo;
import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypapi.service.notification.NotificationService;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.notification.Announcement;
import fyp.canteen.fypcore.pojo.announcement.AnnouncementRequestPojo;
import fyp.canteen.fypcore.pojo.notification.NotificationRequestPojo;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import fyp.canteen.fypcore.utils.UserDataConfig;
import fyp.canteen.fypcore.utils.pagination.CustomPaginationHandler;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService{
    private final AnnouncementRepo announcementRepo;
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
    private final UserRepo userRepo;
    private final UserDataConfig userDataConfig;
    private final NotificationService notificationService;
    private final CustomPaginationHandler customPaginationHandler;

    @Override
    @Transactional
    public void saveAnnouncement(AnnouncementRequestPojo requestPojo) {
        Announcement announcement = new Announcement();

        try{
            beanUtilsBean.copyProperties(announcement, requestPojo);
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }

        announcementRepo.save(announcement);

        String modifyMessage = "Announcement: \"" + requestPojo.getMessage() + "\"";
        requestPojo.setMessage(modifyMessage);
        List<Long> usersToAnnounce = userRepo.getAllIdOfUsers(userDataConfig.userId());
        notificationService.saveNotificationForMultipleUser(NotificationRequestPojo.builder()
                        .message(requestPojo.getMessage())
                .build(), usersToAnnounce);

    }

    @Override
    public PaginationResponse announcementPaginated(PaginationRequest paginationRequest) {
        return customPaginationHandler.getPaginatedData(announcementRepo.getAllAnnouncementPaginated(paginationRequest.getPageable()));
    }
}
