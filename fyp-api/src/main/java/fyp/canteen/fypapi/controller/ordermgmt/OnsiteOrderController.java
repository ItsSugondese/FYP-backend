package fyp.canteen.fypapi.controller.ordermgmt;

import fyp.canteen.fypapi.service.ordermgmt.OnsiteOrderService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.ApprovalStatus;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.enums.OrderType;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.ordermgmt.OnsiteOrderRequestPojo;
import fyp.canteen.fypcore.pojo.pagination.OnsiteOrderPaginationRequestPojo;
import fyp.canteen.fypcore.pojo.pagination.OrderDetailsPaginationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/onsite-order")
@Tag(name = ModuleNameConstants.ONSITE_ORDER)
public class OnsiteOrderController extends BaseController {

    private final OnsiteOrderService onsiteOrderService;

    public OnsiteOrderController(OnsiteOrderService onsiteOrderService) {
        this.onsiteOrderService = onsiteOrderService;
        this.moduleName = ModuleNameConstants.ONSITE_ORDER;
    }

    @PostMapping
    @Operation(summary = "Use this api to save/update onsite order", responses = {@ApiResponse(responseCode = "200")})
    public ResponseEntity<GlobalApiResponse> saveOnsiteOrder(@RequestBody @Valid OnsiteOrderRequestPojo requestPojo){
        onsiteOrderService.saveOnsiteOrder(requestPojo, OrderType.ONSITE);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName),
                CRUD.SAVE, null));
    }
    @GetMapping("/verify-onsite")
    @Operation(summary = "Use verfiy the QR code value",
            responses = {@ApiResponse(responseCode = "200")})
    public String getOnsiteUrl(){
        return "/onsite-order/verify";
    }

    @PostMapping("/paginated")
    public ResponseEntity<GlobalApiResponse> getOnisteOrderPaginated(@RequestBody OnsiteOrderPaginationRequestPojo requestPojo){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, onsiteOrderService.getPaginatedOrderListByTime(requestPojo)
        ));
    }

    @GetMapping("/mark-as-read/{id}")
    public ResponseEntity<GlobalApiResponse> markOnsiteOrderAsRead(@PathVariable("id") Long id){
        onsiteOrderService.markOnsiteOrderAsRead(id);
        return ResponseEntity.ok(successResponse("Order Marked as read",
                CRUD.SAVE, null));
    }

    @GetMapping("/status/{id}/{status}")
    public ResponseEntity<GlobalApiResponse> orderApprovalStatus(@PathVariable("id") Long id,
                                                                 @PathVariable("status")ApprovalStatus status){
        onsiteOrderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(successResponse("Selected order is sent to " + status.getText() + ".",
                CRUD.SAVE, null));
    }

    @PostMapping("/history/paginated")
    public ResponseEntity<GlobalApiResponse> getUserOrder(@RequestBody OrderDetailsPaginationRequest requestPojo){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, onsiteOrderService.getPaginatedOrderHistoryDetails(requestPojo)
        ));
    }
}
