package fyp.canteen.fypapi.controller.ordermgmt;

import fyp.canteen.fypapi.service.ordermgmt.OrderUserMappingService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.OrderType;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.ordermgmt.OnlineOrderRequestPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderUserMappingRequestPojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/onsite-order")
@Tag(name = ModuleNameConstants.ONSITE_ORDER)
public class OnsiteOrderController extends BaseController {

    private final OrderUserMappingService orderUserMappingService;

    public OnsiteOrderController(OrderUserMappingService orderUserMappingService) {
        this.orderUserMappingService = orderUserMappingService;
        this.moduleName = ModuleNameConstants.ONSITE_ORDER;
    }

    @PostMapping
    @Operation(summary = "Use this api to save/update onsite order", responses = {@ApiResponse(responseCode = "200")})
    public ResponseEntity<GlobalApiResponse> saveOnsiteOrder(@RequestBody @Valid OrderUserMappingRequestPojo requestPojo){
        orderUserMappingService.saveOnsiteOrder(requestPojo, OrderType.ONSITE);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName), null));
    }
    @GetMapping("/verify-onsite")
    @Operation(summary = "Use verfiy the QR code value",
            responses = {@ApiResponse(responseCode = "200")})
    public String getOnsiteUrl(){
        return "/onsite-order/verify";
    }
}
