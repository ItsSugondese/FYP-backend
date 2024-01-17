package fyp.canteen.fypapi.service.usermgmt;

import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.pojo.usermgmt.UserDetailPaginationRequest;
import fyp.canteen.fypcore.pojo.usermgmt.UserDetailsRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;

public interface UserService {

    void saveUser(UserDetailsRequestPojo requestPojo);

    void saveUserFromGoogleLogin(UserDetailsRequestPojo requestPojo);

    User findUserByEmail(String email);

    User findUserById(Long id);

    PaginationResponse getAllUsersPaginated(UserDetailPaginationRequest paginationRequest);
}
