package fyp.canteen.fypapi.controller.payment;

import fyp.canteen.fypapi.service.payment.UserPaymentDetailsService;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.generics.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@Tag(name = ModuleNameConstants.PAYMENT)
public class UserPaymentController extends BaseController {
    private final UserPaymentDetailsService userPaymentDetailsService;

    public UserPaymentController(UserPaymentDetailsService userPaymentDetailsService) {
        this.userPaymentDetailsService = userPaymentDetailsService;
        this.moduleName = ModuleNameConstants.PAYMENT;
    }
}
