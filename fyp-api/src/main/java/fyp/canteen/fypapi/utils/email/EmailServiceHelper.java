package fyp.canteen.fypapi.utils.email;

import freemarker.template.TemplateException;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.enums.PasswordSetType;
import fyp.canteen.fypcore.pojo.resetpassword.ResetPasswordDetailRequestPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailServiceHelper {
    private final MailSenderService mailSenderService;

    public void sendResetPasswordEmail(ResetPasswordDetailRequestPojo requestPojo) throws TemplateException, IOException {
        String baseUrl = requestPojo.getBaseUrl() == null ? MessageConstants.PASSWORD_RESET_LINK : requestPojo.getBaseUrl() + "/reset-password/";
        String link = baseUrl + requestPojo.getResetToken();
//        CompanyDetailsForMailPojo companyDetailsToSendMail = companyDetailMapper.getCompanyDetailsToSendMail();
        Map<String, Object> model = new HashMap<>();
        model.put("fullName", requestPojo.getFullName());
        model.put("passwordResetLink", link);
//        model.put("imagePath", companyDetailsToSendMail.getLogoPath());
        model.put("expireTime", requestPojo.getPasswordSetType().equals(PasswordSetType.SET) ? "24 hours" : "10 minutes");
        mailSenderService.sendEmailWithTemplate(EmailSenderRequest
                .builder()
                .toEmail(Collections.singletonList(requestPojo.getUserEmail()))
                .subject("Password Reset")
                .templateName("password-reset-mail.ftl")
                .model(model)
                .build());
    }
}
