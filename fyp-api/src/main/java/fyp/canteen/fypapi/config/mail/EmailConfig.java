package fyp.canteen.fypapi.config.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import fyp.canteen.fypapi.utils.email.EmailSenderRequest;
import fyp.canteen.fypcore.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Component
@RequiredArgsConstructor
public class EmailConfig {
    private final Configuration mailConfig;
    private final MailProperties mailProperties;




    public void sendFreemakerTemplateMail(EmailSenderRequest request){
        try {
            Template template = mailConfig.getTemplate(request.getTemplateName());
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, request.getModel());
            request.setContent(html);
            if (request.getAttachmentsUrl() == null)
                this.sendEmail(request);

        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }
    }
    public HtmlEmail simpleJavaMailSender() throws Exception {
        HtmlEmail email = new HtmlEmail();

        email.setHostName(mailProperties.getHost());
        email.setSmtpPort(mailProperties.getPort());
        email.setFrom(mailProperties.getUsername());
        email.setAuthenticator(new DefaultAuthenticator(mailProperties.getUsername(), mailProperties.getPassword()));
        email.setStartTLSEnabled(true);
        email.setDebug(true);
        return email;
    }

    public void sendEmail(EmailSenderRequest request) {
        try {
            request.getToEmail().forEach(to -> {
                HtmlEmail email = null;
                try {
                    email =  simpleJavaMailSender();
                    email.setSubject(request.getSubject());
                    email.setHtmlMsg(request.getContent() == null ? "" : request.getContent());
                    email.addTo(to);
                    if (request.getCcEmail() != null && !request.getCcEmail().isEmpty())
                        email.setCc(parseEmail(request.getCcEmail()));
                    email.send();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Collection<InternetAddress> parseEmail(List<String> emailIds) {
        Collection<InternetAddress> internetAddressList = new ArrayList<>();
        emailIds.forEach(s -> {
            try {
                InternetAddress internetAddress = new InternetAddress(s);
                internetAddressList.add(internetAddress);
            } catch (AddressException e) {
                e.printStackTrace();
            }
        });
        return internetAddressList;
    }
}