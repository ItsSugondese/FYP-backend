package fyp.canteen.fypapi.service.announcement;

import fyp.canteen.fypcore.pojo.announcement.AnnouncementRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;

public interface AnnouncementService {

    void saveAnnouncement(AnnouncementRequestPojo requestPojo);

    PaginationResponse announcementPaginated(PaginationRequest paginationRequest);
}
