package fyp.canteen.fypapi.service.resetpassword;

import fyp.canteen.fypapi.mapper.usermgmt.UserDetailMapper;
import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypapi.repository.usermgmt.passwordreset.PasswordResetTokenRepo;
import fyp.canteen.fypcore.enums.PasswordSetType;
import fyp.canteen.fypcore.model.auth.PasswordResetToken;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.pojo.resetpassword.ResetPasswordDetailRequestPojo;
import fyp.canteen.fypcore.pojo.usermgmt.UserBasicDetailResponsePojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService{
    private final UserRepo userRepo;
    private final PasswordResetTokenRepo passwordResetTokenRepo;
    private final UserDetailMapper userDetailMapper;

    public ResetPasswordServiceImpl(UserRepo userRepo, PasswordResetTokenRepo passwordResetTokenRepo, UserDetailMapper userDetailMapper) {
        this.userRepo = userRepo;
        this.passwordResetTokenRepo = passwordResetTokenRepo;
        this.userDetailMapper = userDetailMapper;
    }

    @Override
    @Transactional
    public ResetPasswordDetailRequestPojo resetPasswordEmailVerify(ResetPasswordDetailRequestPojo requestPojo) {
        User user = userRepo.findByEmail(requestPojo.getUserEmail()).orElse(null);
        if (user != null) {
            String token = generateAndSaveToken(requestPojo.getPasswordSetType(), user.getId());
            requestPojo.setResetToken(token);
            requestPojo.setFullName(user.getFullName());
            return requestPojo;
        } else
            return null;
    }

    public String generateAndSaveToken(PasswordSetType passwordSetType, Long userId) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(User.builder().id(userId).build());
        passwordResetToken.setExpiryDate(passwordSetType == PasswordSetType.SET ? LocalDateTime.now().plusHours(24) : LocalDateTime.now().plusMinutes(10));
        passwordResetToken.setStatus(1);
        passwordResetTokenRepo.saveAndFlush(passwordResetToken);
        return token;
    }
}
