package fyp.canteen.fypapi.controller.auth;

import fyp.canteen.fypapi.service.auth.AuthService;
import fyp.canteen.fypapi.service.usermgmt.UserServiceHelper;
import fyp.canteen.fypapi.service.usermgmt.UserServiceImpl;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.enums.PasswordSetType;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.jwt.JwtRequest;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.resetpassword.ResetPasswordDetailRequestPojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
@Tag(name = ModuleNameConstants.AUTH)
public class AuthController extends BaseController {

    private final AuthService authService;
    private final UserServiceHelper userServiceHelper;

    public AuthController(AuthService authService, UserServiceHelper userServiceHelper){
        this.authService = authService;
        this.userServiceHelper = userServiceHelper;
        this.moduleName = ModuleNameConstants.AUTH;

    }


    @PostMapping("/login-with-google")
    @Operation(summary = "Use this api to save ", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> signInWithGoogle(@RequestBody String credential){
        return ResponseEntity.ok(successResponse("Authentication successful", CRUD.SAVE, authService.signInWithGoogle(credential)));
    }

    @PostMapping("/login")
    @Operation(summary = "Use this api to save ", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> signIn(@RequestBody @Valid JwtRequest request){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName),CRUD.SAVE, authService.signIn(request)));
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Use this api to save ", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> signIn(@RequestBody @Valid ResetPasswordDetailRequestPojo request){
        CompletableFuture<String> futureResult = CompletableFuture.supplyAsync(() -> {
            // Simulate a long-running computation
            userServiceHelper.resetPasswordMailSendHelper(
                    ResetPasswordDetailRequestPojo.builder()
                            .userEmail(request.getUserEmail())
                            .passwordSetType(PasswordSetType.RESET)
                            .build()
            );
            return "Future Result";
        });
        return ResponseEntity.ok(successResponse("Check your email to reset password",CRUD.SAVE, null));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<GlobalApiResponse> validateToken(@RequestBody ResetPasswordDetailRequestPojo requestPojo) {
        return ResponseEntity.ok(successResponse("Token Validated",
                CRUD.SAVE,
                userServiceHelper.validateTokenHelper(requestPojo)));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<GlobalApiResponse> resetPassword(@RequestBody ResetPasswordDetailRequestPojo requestPojo) {
        return ResponseEntity.ok(successResponse("Password Changed Successfully",
                CRUD.SAVE,
                userServiceHelper.resetPasswordHelper(requestPojo)));
    }
}
