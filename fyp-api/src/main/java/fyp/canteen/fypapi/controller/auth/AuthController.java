package fyp.canteen.fypapi.controller.auth;

import fyp.canteen.fypapi.service.auth.AuthService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
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

@RestController
@RequestMapping("/auth")
@Tag(name = ModuleNameConstants.AUTH)
public class AuthController extends BaseController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
        this.moduleName = ModuleNameConstants.AUTH;
    }


    @PostMapping("/login-with-google")
    @Operation(summary = "Use this api to save ", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> signInWithGoogle(@RequestBody String credential){
        return ResponseEntity.ok(successResponse(Message.Crud(MessageConstants.SAVE, moduleName), authService.signInWithGoogle(credential)));
    }

}
