package fyp.canteen.fypapi.controller.payment;

import fyp.canteen.fypapi.service.payment.KhatiService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.payment.KhaltiTransactionVerificationRequestPojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/khalti")
@Tag(name = ModuleNameConstants.KHALTI)
public class KhaltiController extends BaseController {

    private final KhatiService khatiService;

    public KhaltiController(KhatiService khatiService) {
        this.khatiService = khatiService;
        this.moduleName = ModuleNameConstants.KHALTI;
    }

    @PostMapping("/verify")
    @Operation(summary = "Use this api to verify transaction made using khalti in frontend",
            responses = {@ApiResponse(responseCode = "200")})
    public ResponseEntity<GlobalApiResponse> verifyTransaction(@RequestBody @Valid KhaltiTransactionVerificationRequestPojo requestPojo){
        return ResponseEntity.ok(successResponse("Transaction successful",
                CRUD.SAVE, khatiService.verifyTransaction(requestPojo)));
    }
}
