package fyp.canteen.fypapi.service.usermgmt;

import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.pojo.usermgmt.*;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    void saveUserFromGoogleLogin(UserDetailsRequestPojo requestPojo);

    User findUserByEmail(String email);

    User findUserById(Long id);

    UserDetailResponsePojo getSingleUserById(Long id);

    PaginationResponse getAllUsersPaginated(UserDetailPaginationRequest paginationRequest);

}
