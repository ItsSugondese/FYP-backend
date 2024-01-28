package fyp.canteen.fypapi.service.resetpassword;

import fyp.canteen.fypcore.pojo.resetpassword.ResetPasswordDetailRequestPojo;

public interface ResetPasswordService {
    ResetPasswordDetailRequestPojo resetPasswordEmailVerify(ResetPasswordDetailRequestPojo requestPojo);
}
