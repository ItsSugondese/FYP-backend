package fyp.canteen.fypapi.service.resetpassword;

import fyp.canteen.fypapi.mapper.usermgmt.UserDetailMapper;
import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypapi.repository.usermgmt.passwordreset.PasswordResetTokenRepo;
import fyp.canteen.fypcore.enums.PasswordSetType;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.auth.PasswordResetToken;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.pojo.resetpassword.ResetPasswordDetailRequestPojo;
import fyp.canteen.fypcore.pojo.usermgmt.UserBasicDetailResponsePojo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService{
    private final UserRepo userRepo;
    private final PasswordResetTokenRepo passwordResetTokenRepo;
    private final UserDetailMapper userDetailMapper;
    private final PasswordEncoder passwordEncoder;



    @Override
    public ResetPasswordDetailRequestPojo validatePasswordResetToken(ResetPasswordDetailRequestPojo requestPojo) {
        PasswordResetToken resetToken = validateToken(requestPojo.getResetToken());
        requestPojo.setResetToken(resetToken.getToken());
        return requestPojo;
    }

    @Override
    public PasswordResetToken validateToken(String token) {
        return passwordResetTokenRepo.findByToken(token).orElseThrow(() -> new AppException("Invalid Token"));
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

    @Override
    @Transactional
    public ResetPasswordDetailRequestPojo resetPassword(ResetPasswordDetailRequestPojo requestPojo) {
        PasswordResetToken resetToken = validateToken(requestPojo.getResetToken());
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(requestPojo.getPassword()));
//        user.setLastPasswordChangedAt(LocalDateTime.now());
        userRepo.saveAndFlush(user);
//        requestPojo.setUserEmail(user.getEmailAddress());
        requestPojo.setFullName(user.getFullName());
        if (requestPojo.getResetToken() != null) passwordResetTokenRepo.updateTokenIsActive(resetToken.getToken());
        return requestPojo;
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
