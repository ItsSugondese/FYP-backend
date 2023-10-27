package fyp.canteen.fypcore.pojo.temporaryattachments;

import fyp.canteen.fypcore.enums.FileType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TemporaryAttachmentsDetailResponsePojo {
    private Long id;

    private String name;

    private String location;

    private Float fileSize;

    private FileType fileType;
}

