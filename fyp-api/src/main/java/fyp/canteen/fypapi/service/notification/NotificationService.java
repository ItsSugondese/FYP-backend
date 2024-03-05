package fyp.canteen.fypapi.service.notification;

import fyp.canteen.fypcore.pojo.notification.NotificationRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;

public interface NotificationService {
    void saveNotification(NotificationRequestPojo requestPojo);

    PaginationResponse getAllNotificationOfMember(PaginationRequest request);
    void markAsSeen();
    Integer newNotifications();

}
