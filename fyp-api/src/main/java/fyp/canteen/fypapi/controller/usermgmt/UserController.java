package fyp.canteen.fypapi.controller.usermgmt;

import fyp.canteen.fypapi.service.usermgmt.UserService;
import fyp.canteen.fypapi.service.usermgmt.disable.UserDisableHistoryService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.usermgmt.UserDetailPaginationRequest;
import fyp.canteen.fypcore.pojo.usermgmt.disable.UserDisableHistoryPaginationRequest;
import fyp.canteen.fypcore.pojo.usermgmt.disable.UserDisableHistoryRequestPojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Tag(name = ModuleNameConstants.USER)
public class UserController extends BaseController {
    private final UserService userService;
    private final UserDisableHistoryService userDisableHistoryService;
    public UserController(UserService userService, UserDisableHistoryService userDisableHistoryService){
        this.moduleName = ModuleNameConstants.USER;
        this.userDisableHistoryService = userDisableHistoryService;
        this.userService = userService;
    }

    @PostMapping("/paginated")
    @Operation(summary = "Use this api to save/update food menu details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getFoodMenu(@RequestBody UserDetailPaginationRequest requestPojo){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName),
                CRUD.GET, userService.getAllUsersPaginated(requestPojo)
        ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Use this api to get single staff", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getSingleStaff(@PathVariable("id") Long id){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, userService.getSingleUserById(id)));
    }

    @PostMapping("/disable")
    @Operation(summary = "Use this api to get single staff", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getSingleStaff(@RequestBody UserDisableHistoryRequestPojo requestPojo){
        userDisableHistoryService.saveUserDisableHistory(requestPojo);
        return ResponseEntity.ok(successResponse("User has been disabled successfully",
                CRUD.SAVE, true));
    }

    @PostMapping("/disable/pageable")
    @Operation(summary = "Use this api to get single staff", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getSingleStaff(@RequestBody UserDisableHistoryPaginationRequest requestPojo){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, userDisableHistoryService.getDisableHistoryPaginated(requestPojo)));
    }
}
