package fyp.canteen.fypapi.controller.usermgmt.profile;

import fyp.canteen.fypapi.service.usermgmt.UserService;
import fyp.canteen.fypapi.service.usermgmt.disable.UserDisableHistoryService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.usermgmt.UserDetailPaginationRequest;
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
@RequestMapping("/user/profile")
@Tag(name = ModuleNameConstants.USER_PROFILE)
public class UserProfileController extends BaseController {
    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.moduleName = ModuleNameConstants.USER_PROFILE;
        this.userService = userService;
    }


    @GetMapping
    @Operation(summary = "Use this api to get logged in user details", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getSingleStaff() {
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, userService.getSingleUserWithoutId()));
    }
}
