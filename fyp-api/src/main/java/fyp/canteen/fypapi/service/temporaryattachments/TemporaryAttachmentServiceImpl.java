package fyp.canteen.fypapi.service.temporaryattachments;

import fyp.canteen.fypapi.repository.temporaryattachments.TemporaryAttachmentsRepo;
import fyp.canteen.fypcore.enums.FileType;
import fyp.canteen.fypcore.model.temporaryattachments.TemporaryAttachments;
import fyp.canteen.fypcore.pojo.temporaryattachments.AttachmentBasicResponsePojo;
import fyp.canteen.fypcore.pojo.temporaryattachments.TemporaryAttachmentsDetailRequestPojo;
import fyp.canteen.fypcore.utils.genericfile.GenericFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TemporaryAttachmentServiceImpl implements TemporaryAttachmentService{

    private final TemporaryAttachmentsRepo temporaryAttachmentsRepo;
    private final GenericFileUtil genericFileUtil;

    @Override
    public List<Long> saveTemporaryAttachment(TemporaryAttachmentsDetailRequestPojo detailRequestPojo) throws Exception {
        List<Long> savedTemporaryAttachmentId = new ArrayList<>();

        /**
         * Save and return temporary file id when temporary attachment save api called
         */
        int i = 0;
        if (detailRequestPojo.getAttachments() != null) {
            for (MultipartFile ticketAttachment : detailRequestPojo.getAttachments()
            ) {
                Map<String, Object> ticketAttachments = genericFileUtil.saveTempFile(ticketAttachment,
                        List.of(FileType.IMAGE, FileType.DOC, FileType.PDF, FileType.EXCEL));
                String filename = ticketAttachment.getOriginalFilename();


                Float fileSize = (float) ticketAttachment.getSize() / (1024 * 1024);
                saveTemporaryFile(savedTemporaryAttachmentId, filename, fileSize, (FileType) ticketAttachments.get("fileType"),(String) ticketAttachments.get("location"), detailRequestPojo.getName()!=null?detailRequestPojo.getName().get(i):filename);
                i++;
            }
        }
        /**
         * Save and return temporary file id when response email api called
         */
        else if (detailRequestPojo.getResponsePojo() != null) {
            for (AttachmentBasicResponsePojo responsePojo : detailRequestPojo.getResponsePojo()) {
                saveTemporaryFile(savedTemporaryAttachmentId, responsePojo.getName(), null, null, responsePojo.getFilePaths(), null);
            }
        }
        return savedTemporaryAttachmentId;
    }

    private void saveTemporaryFile(List<Long> savedTemporaryAttachmentId, String fileName, Float fileSize, FileType fileType, String ticketAttachments, String originalFileName) {
        TemporaryAttachments temporaryAttachments;
        temporaryAttachments = TemporaryAttachments.builder()
                .location(ticketAttachments)
                .name(originalFileName != null ? originalFileName : fileName)
                .fileSize(fileSize)
                .fileType(fileType)
                .build();
        TemporaryAttachments savedTemporaryAttachments = temporaryAttachmentsRepo.save(temporaryAttachments);
        savedTemporaryAttachmentId.add(savedTemporaryAttachments.getId());
    }
}
