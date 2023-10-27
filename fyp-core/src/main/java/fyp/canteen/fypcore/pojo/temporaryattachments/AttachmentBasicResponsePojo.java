package fyp.canteen.fypcore.pojo.temporaryattachments;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachmentBasicResponsePojo {

    private String filePaths;

    private String name;

    private Float fileSize;
}
