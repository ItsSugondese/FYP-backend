package fyp.canteen.fypapi.controller;

import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.jwt.JwtRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController

public class CanteenController {

    @GetMapping("/filter")
    @Operation(summary = "Use this api to save ", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Boolean.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public String signIn(){
        return "Hello There";
    }


}
