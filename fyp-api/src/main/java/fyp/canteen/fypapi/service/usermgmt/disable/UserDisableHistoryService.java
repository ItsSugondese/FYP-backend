package fyp.canteen.fypapi.service.usermgmt.disable;

import fyp.canteen.fypcore.pojo.usermgmt.UserDetailPaginationRequest;
import fyp.canteen.fypcore.pojo.usermgmt.disable.UserDisableHistoryPaginationRequest;
import fyp.canteen.fypcore.pojo.usermgmt.disable.UserDisableHistoryRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;

public interface UserDisableHistoryService {

    void saveUserDisableHistory(UserDisableHistoryRequestPojo requestPojo);
    PaginationResponse getDisableHistoryPaginated(UserDisableHistoryPaginationRequest paginationRequest);
}
