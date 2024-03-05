package fyp.canteen.fypapi.controller.notification;

import fyp.canteen.fypapi.service.notification.NotificationService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@Tag(name = ModuleNameConstants.NOTIFICATION)
public class NotificationController extends BaseController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
        this.moduleName = ModuleNameConstants.NOTIFICATION;
    }

    @PostMapping("/paginated")
    @Operation(summary = "Get All notification of particular member")
    public ResponseEntity<GlobalApiResponse> getAllNotificationOfMember(@RequestBody PaginationRequest request) {
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET,
                notificationService.getAllNotificationOfMember(request)));
    }

    @GetMapping("/mark-as-read")
    @Operation(summary = "Mark all notification as seen")
    public ResponseEntity<GlobalApiResponse> markNotificationAsSeen(PaginationRequest request) {
        notificationService.markAsSeen();
        return ResponseEntity.ok(successResponse("All " + moduleName + " mark as read.",
                CRUD.SAVE, true));
    }

    @GetMapping("/new-notification-count")
    @Operation(summary = "Mark all notification as seen")
    public ResponseEntity<GlobalApiResponse> getNewNotificationCount(PaginationRequest request) {
        return ResponseEntity.ok(successResponse("All " + moduleName + " mark as read.",
                CRUD.GET, notificationService.newNotifications()));
    }
}

