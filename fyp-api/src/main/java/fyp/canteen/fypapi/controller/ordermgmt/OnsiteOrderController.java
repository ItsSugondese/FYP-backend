package fyp.canteen.fypapi.controller.ordermgmt;

import fyp.canteen.fypapi.service.dashboard.admin.AdminDashboardService;
import fyp.canteen.fypapi.service.ordermgmt.OnsiteOrderService;
import fyp.canteen.fypapi.service.table.TableService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.ApprovalStatus;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.enums.OrderType;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.ordermgmt.OnsiteOrderRequestPojo;
import fyp.canteen.fypcore.pojo.pagination.OnsiteOrderOfUserPaginationRequestPojo;
import fyp.canteen.fypcore.pojo.pagination.OnsiteOrderPaginationRequestPojo;
import fyp.canteen.fypcore.pojo.pagination.OrderDetailsPaginationRequest;
import fyp.canteen.fypcore.pojo.pagination.OrderHistoryPaginationRequest;
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
    private final TableService tableService;
    private final AdminDashboardService adminDashboardService;

    public OnsiteOrderController(OnsiteOrderService onsiteOrderService, TableService tableService,
                                 AdminDashboardService adminDashboardService) {
        this.onsiteOrderService = onsiteOrderService;
        this.tableService = tableService;
        this.adminDashboardService = adminDashboardService;
        this.moduleName = ModuleNameConstants.ONSITE_ORDER;
    }

    @PostMapping
    @Operation(summary = "Use this api to save/update onsite order", responses = {@ApiResponse(responseCode = "200")})
    public ResponseEntity<GlobalApiResponse> saveOnsiteOrder(@RequestBody @Valid OnsiteOrderRequestPojo requestPojo){
        onsiteOrderService.saveOnsiteOrder(requestPojo);
        adminDashboardService.sendFoodMenuDataSocket();
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName),
                CRUD.SAVE, true));
    }
    @GetMapping("/verify-onsite/{code}")
    @Operation(summary = "Use verfiy the QR code value",
            responses = {@ApiResponse(responseCode = "200")})
    public ResponseEntity<GlobalApiResponse> getOnsiteUrl(@PathVariable("code") String code){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, tableService.verifyOnsite(code)
        ));
    }

    @PostMapping("/paginated")
    public ResponseEntity<GlobalApiResponse> getOnsiteOrderPaginated(@RequestBody OnsiteOrderPaginationRequestPojo requestPojo){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, onsiteOrderService.getPaginatedOrderListByTime(requestPojo)
        ));
    }

    @PostMapping("/order-history/paginated")
    public ResponseEntity<GlobalApiResponse> getAllOrderHistoryPaginated(@RequestBody OrderHistoryPaginationRequest requestPojo){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, onsiteOrderService.getPaginatedOrderHistoryOfAllUsers(requestPojo)
        ));
    }

    @PostMapping("/by-user/paginated")
    public ResponseEntity<GlobalApiResponse> getOnsiteOrderOfUserPaginated(@RequestBody OnsiteOrderOfUserPaginationRequestPojo requestPojo){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, onsiteOrderService.getPaginatedOrderOfUser(requestPojo)
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
