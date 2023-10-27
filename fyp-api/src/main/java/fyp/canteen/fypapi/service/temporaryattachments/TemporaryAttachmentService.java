package fyp.canteen.fypapi.service.temporaryattachments;

import fyp.canteen.fypcore.pojo.temporaryattachments.TemporaryAttachmentsDetailRequestPojo;

import java.util.List;

public interface TemporaryAttachmentService {

    List<Long> saveTemporaryAttachment(TemporaryAttachmentsDetailRequestPojo detailRequestPojo) throws Exception;
}
