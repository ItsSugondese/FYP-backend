package fyp.canteen.fypapi.controller.temporaryattachments;

import fyp.canteen.fypapi.service.temporaryattachments.TemporaryAttachmentService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.temporaryattachments.TemporaryAttachmentsDetailRequestPojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RequestMapping("/temporary-attachments")
@Slf4j
@RestController
@Tag(name = ModuleNameConstants.TEMPORARY_ATTACHMENTS)
public class TemporaryAttachmentsController extends BaseController {
    private final TemporaryAttachmentService temporaryAttachmentService;

    public TemporaryAttachmentsController(TemporaryAttachmentService temporaryAttachmentService) {
        this.temporaryAttachmentService = temporaryAttachmentService;
        this.moduleName = ModuleNameConstants.TEMPORARY_ATTACHMENTS;

    }
    @PostMapping
    @Operation(summary = "Use this api to post the attachments when and where ever required.",
            description = "This api will accept multipart file. The generated id is needed to pass to the actual pojo where you want to pass the attachments." +
                    "For e.g to save student's photo, first use this api to post photo and the id will be returned, now pass that id in student detail request pojo's photoId key.", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = MultipartFile.class)))})})
    public ResponseEntity<GlobalApiResponse> saveTemporaryAttachments(@ModelAttribute @Valid TemporaryAttachmentsDetailRequestPojo requestPojo) throws Exception {
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName),
                null, temporaryAttachmentService.saveTemporaryAttachment(requestPojo)));
    }
}
