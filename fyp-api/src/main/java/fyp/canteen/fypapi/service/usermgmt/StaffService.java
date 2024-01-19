package fyp.canteen.fypapi.service.usermgmt;

import fyp.canteen.fypcore.pojo.usermgmt.StaffDetailPaginationRequest;
import fyp.canteen.fypcore.pojo.usermgmt.StaffDetailResponsePojo;
import fyp.canteen.fypcore.pojo.usermgmt.StaffDetailsRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface StaffService {

    void saveStaff(StaffDetailsRequestPojo requestPojo);
    PaginationResponse getAllStaffPaginated(StaffDetailPaginationRequest paginationRequest);

    StaffDetailResponsePojo getSingleStaffById(Long id);

    void getStaffPhoto(HttpServletResponse response, Long id);
}
