package fyp.canteen.fypapi.service.usermgmt;

import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypapi.service.auth.RoleService;
import fyp.canteen.fypapi.service.resetpassword.ResetPasswordService;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.auth.Role;
import fyp.canteen.fypcore.pojo.resetpassword.ResetPasswordDetailRequestPojo;
import fyp.canteen.fypapi.utils.email.EmailServiceHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceHelperImpl implements UserServiceHelper{
    private final RoleService roleService;
    private final UserRepo userRepo;
    private final ResetPasswordService resetPasswordService;
    private final EmailServiceHelper emailServiceHelper;

    @Override
    public void checkForUniqueEmail(String email) {
        String checkUnique = userRepo.isUnique(email.toUpperCase());
        if (checkUnique != null)
            throw new AppException(checkUnique);
    }

    public Set<Role> getRoles(String role){
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findRoleByName(role));
        return roles;
    }

    @Override
    public ResetPasswordDetailRequestPojo resetPasswordMailSendHelper(ResetPasswordDetailRequestPojo requestPojo) {

        try {
            resetPasswordService.resetPasswordEmailVerify(requestPojo);
            if (requestPojo.getResetToken() != null)
                emailServiceHelper.sendResetPasswordEmail(requestPojo);
            return null;
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }
    }


    @Override
    @Transactional
    public ResetPasswordDetailRequestPojo validateTokenHelper(ResetPasswordDetailRequestPojo requestPojo) {
        try {
            resetPasswordService.validatePasswordResetToken(requestPojo);
            return requestPojo;
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Boolean resetPasswordHelper(ResetPasswordDetailRequestPojo requestPojo) {
        try {
            resetPasswordService.resetPassword(requestPojo);
            return true;
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }
    }
}
