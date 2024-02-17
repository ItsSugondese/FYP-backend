package fyp.canteen.fypapi.controller.payment;

import fyp.canteen.fypapi.service.payment.UserPaymentDetailsService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.payment.KhaltiTransactionVerificationRequestPojo;
import fyp.canteen.fypcore.pojo.payment.UserPaymentDetailsRequestPojo;
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
@RequestMapping("/payment")
@Tag(name = ModuleNameConstants.PAYMENT)
public class UserPaymentController extends BaseController {
    private final UserPaymentDetailsService userPaymentDetailsService;

    public UserPaymentController(UserPaymentDetailsService userPaymentDetailsService) {
        this.userPaymentDetailsService = userPaymentDetailsService;
        this.moduleName = ModuleNameConstants.PAYMENT;
    }

    @PostMapping
    @Operation(summary = "Use this api to verify transaction made using khalti in frontend",
            responses = {@ApiResponse(responseCode = "200")})
    public ResponseEntity<GlobalApiResponse> savePaymentDetails(@RequestBody @Valid UserPaymentDetailsRequestPojo requestPojo){
        userPaymentDetailsService.savePayment(requestPojo);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName),
                CRUD.SAVE, null));
    }
}
