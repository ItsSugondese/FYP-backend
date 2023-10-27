package fyp.canteen.fypcore.pojo.temporaryattachments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemporaryAttachmentsDetailRequestPojo {
    private Long id;
    private List<MultipartFile> attachments;
    @JsonIgnore
    private List<String> filePaths;
    @JsonIgnore
    private List<String> name;
    private List<AttachmentBasicResponsePojo> responsePojo;
}
