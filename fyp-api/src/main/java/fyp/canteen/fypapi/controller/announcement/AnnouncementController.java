package fyp.canteen.fypapi.controller.announcement;

import fyp.canteen.fypapi.service.announcement.AnnouncementService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.announcement.AnnouncementRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/announcement")
@Tag(name = ModuleNameConstants.ANNOUNCEMENT)
public class AnnouncementController extends BaseController {
    private final AnnouncementService announcementService;
    public AnnouncementController(AnnouncementService announcementService){
        this.announcementService = announcementService;
        this.moduleName = ModuleNameConstants.ANNOUNCEMENT;
    }
    @PostMapping
    @Operation(summary = "Post announcement")
    public ResponseEntity<GlobalApiResponse> saveAnnouncement(@RequestBody AnnouncementRequestPojo requestPojo) {
        announcementService.saveAnnouncement(requestPojo);
        return ResponseEntity.ok(successResponse(moduleName + " posted successfully.",
                CRUD.SAVE,
                null));
    }

    @PostMapping("/paginated")
    @Operation(summary = "Get All notification of particular member")
    public ResponseEntity<GlobalApiResponse> getAllANnouncemeentPaginated(@RequestBody PaginationRequest request) {
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET,
                announcementService.announcementPaginated(request)));
    }

}
