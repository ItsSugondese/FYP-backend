package fyp.canteen.fypapi.utils.email;

import fyp.canteen.fypcore.pojo.AttachmentDetailsPojo;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailSenderRequest {
    private String subject;
    private List<String> toEmail;
    private List<String> ccEmail;
    private String content;
    private Map<String, Object> model;
    // this field is being used by feedback service
    private List<AttachmentDetailsPojo> attachmentsLocation;
    private List<String> attachmentsUrl;
    private String templateName;
    private String domain;
}
