package fyp.canteen.fypapi.service.resetpassword;

import fyp.canteen.fypcore.model.auth.PasswordResetToken;
import fyp.canteen.fypcore.pojo.resetpassword.ResetPasswordDetailRequestPojo;

public interface ResetPasswordService {
    ResetPasswordDetailRequestPojo resetPasswordEmailVerify(ResetPasswordDetailRequestPojo requestPojo);

    ResetPasswordDetailRequestPojo validatePasswordResetToken(ResetPasswordDetailRequestPojo requestPojo);

    PasswordResetToken validateToken(String token);

    ResetPasswordDetailRequestPojo resetPassword(ResetPasswordDetailRequestPojo requestPojo);
}
