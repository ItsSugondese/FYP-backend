package fyp.canteen.fypapi.service.notification;

import fyp.canteen.fypcore.pojo.notification.NotificationRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;

import java.util.List;

public interface NotificationService {
    void saveNotification(NotificationRequestPojo requestPojo);
    void saveNotificationForMultipleUser(NotificationRequestPojo requestPojo, List<Long> userIds);

    PaginationResponse getAllNotificationOfMember(PaginationRequest request);
    void markAsSeen();
    Integer newNotifications();

}
