package fyp.canteen.fypapi.mapper;

import fyp.canteen.fypcore.pojo.temporaryattachments.TemporaryAttachmentsDetailResponsePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TemporaryAttachmentsDetailMapper {

    @Select("select ta.id as id, ta.name as name, ta.location as location,ta.file_size as fileSize, ta.file_type as fileType from temporary_attachments" +
            " ta where ta.id=#{id}")
    TemporaryAttachmentsDetailResponsePojo getTemporaryAttachmentsById(Long id);

    @Select("select ta.is_active isActive from temporary_attachments ta where ta.id=#{id}")
    Boolean isTemporaryAttachmentMoved(Long id);
}

