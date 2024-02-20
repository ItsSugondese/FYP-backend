package fyp.canteen.fypapi.service.usermgmt;

import fyp.canteen.fypcore.model.auth.Role;
import fyp.canteen.fypcore.pojo.resetpassword.ResetPasswordDetailRequestPojo;

import java.util.Set;

public interface UserServiceHelper {
    void checkForUniqueEmail(String email);

    ResetPasswordDetailRequestPojo resetPasswordMailSendHelper(ResetPasswordDetailRequestPojo requestPojo);

    ResetPasswordDetailRequestPojo validateTokenHelper(ResetPasswordDetailRequestPojo requestPojo);

    Boolean resetPasswordHelper(ResetPasswordDetailRequestPojo requestPojo);
    Set<Role> getRoles(String role);
}
