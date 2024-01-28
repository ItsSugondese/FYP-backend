package fyp.canteen.fypapi.utils.email;

import fyp.canteen.fypapi.config.mail.EmailConfig;
import fyp.canteen.fypapi.mapper.company.CompanyDetailMapper;
import fyp.canteen.fypcore.pojo.company.CompanyDetailsForMailPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MailSenderService {
    private final CompanyDetailMapper companyDetailMapper;
    private final EmailConfig emailConfig;
    public void sendEmailWithTemplate(EmailSenderRequest request) {
        Map<String, Object> model = request.getModel();
        CompanyDetailsForMailPojo companyDetailsToSendMail = companyDetailMapper.getCompanyDetailsToSendMail();
        model.put("imagePath", companyDetailsToSendMail.getLogoPath());
        model.put("companyName", companyDetailsToSendMail.getCompanyName());
        model.put("contactNumber", companyDetailsToSendMail.getContactNumber());
        model.put("location", companyDetailsToSendMail.getLocation());
        request.setModel(model);
        emailConfig.sendFreemakerTemplateMail(request);
    }

}
