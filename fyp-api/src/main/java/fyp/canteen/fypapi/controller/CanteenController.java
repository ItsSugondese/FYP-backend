package fyp.canteen.fypapi.controller;

import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypapi.service.auth.RoleService;
import fyp.canteen.fypapi.service.dashboard.admin.AdminDashboardService;
import fyp.canteen.fypapi.service.notification.NotificationService;
import fyp.canteen.fypapi.service.resetpassword.ResetPasswordService;
import fyp.canteen.fypapi.service.usermgmt.UserServiceHelperImpl;
import fyp.canteen.fypapi.utils.email.EmailServiceHelper;
import fyp.canteen.fypcore.pojo.resetpassword.ResetPasswordDetailRequestPojo;
import fyp.canteen.fypcore.utils.UserDataConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class CanteenController {

    private final UserDataConfig userDataConfig;
    private final NotificationService notificationService;
    private final AdminDashboardService adminDashboardService;

    @GetMapping("/filter")
    @Operation(summary = "Use this api to save ", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public Object signIn(){
        return "hello";
    }

    @GetMapping
    @Operation(summary = "Use this api to save ", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public void signInSOcket(){
         adminDashboardService.pingOrderSocket();
    }







}
