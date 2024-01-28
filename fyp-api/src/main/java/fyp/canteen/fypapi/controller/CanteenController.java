package fyp.canteen.fypapi.controller;

import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypapi.service.auth.RoleService;
import fyp.canteen.fypapi.service.resetpassword.ResetPasswordService;
import fyp.canteen.fypapi.service.usermgmt.UserServiceHelperImpl;
import fyp.canteen.fypapi.utils.email.EmailServiceHelper;
import fyp.canteen.fypcore.pojo.resetpassword.ResetPasswordDetailRequestPojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class CanteenController {

    private final UserServiceHelperImpl userServiceHelper;

    public CanteenController(RoleService roleService, UserRepo userRepo,
                             ResetPasswordService resetPasswordService, EmailServiceHelper emailServiceHelper) {
        userServiceHelper = new UserServiceHelperImpl(roleService, userRepo, resetPasswordService, emailServiceHelper);
    }

    @GetMapping("/filter")
    @Operation(summary = "Use this api to save ", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public String signIn(){
        return "Hello There";
    }


    @PostMapping("/mail")
    @Operation(summary = "Use this api to save ", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public String sendMail(@RequestBody ResetPasswordDetailRequestPojo requestPojo){
        userServiceHelper.resetPasswordMailSendHelper(requestPojo);
        return "Hello There";
    }




}
