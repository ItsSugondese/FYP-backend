package fyp.canteen.fypapi.controller.usermgmt;

import fyp.canteen.fypapi.service.usermgmt.StaffService;
import fyp.canteen.fypapi.service.usermgmt.UserService;
import fyp.canteen.fypapi.service.usermgmt.disable.UserDisableHistoryService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.usermgmt.StaffDetailPaginationRequest;
import fyp.canteen.fypcore.pojo.usermgmt.StaffDetailsRequestPojo;
import fyp.canteen.fypcore.pojo.usermgmt.UserDetailPaginationRequest;
import fyp.canteen.fypcore.pojo.usermgmt.disable.UserDisableHistoryRequestPojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/staff")
@Tag(name = ModuleNameConstants.STAFF)
public class StaffController extends BaseController {
    private final StaffService staffService;
    private final UserDisableHistoryService userDisableHistoryService;

    public StaffController(StaffService staffService, UserDisableHistoryService userDisableHistoryService) {
        this.staffService = staffService;
        this.userDisableHistoryService = userDisableHistoryService;
        this.moduleName = ModuleNameConstants.STAFF;
    }

    @PostMapping
    @Operation(summary = "Use this api to save/update staff details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> saveStaff(@RequestBody @Valid StaffDetailsRequestPojo requestPojo){
        staffService.saveStaff(requestPojo);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName),
                true
        ));
    }
    @PostMapping("/paginated")
    @Operation(summary = "Use this api to get staff details in paginated form", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getStaffDetails(@RequestBody StaffDetailPaginationRequest  requestPojo){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                staffService.getAllStaffPaginated(requestPojo)
        ));
    }

    @GetMapping("/photo/{id}")
    @Operation(summary = "Use this api to save/update food menu details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getFoodPicture(@PathVariable("id") Long id, HttpServletResponse response){
        staffService.getStaffPhoto(response, id);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName), true));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Use this api to get single staff", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getSingleStaff(@PathVariable("id") Long id){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName), staffService.getSingleStaffById(id)));
    }


}
