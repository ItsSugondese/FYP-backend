package fyp.canteen.fypapi.controller.ordermgmt;

import fyp.canteen.fypapi.mapper.ordermgmt.OrderFoodMappingMapper;
import fyp.canteen.fypapi.service.ordermgmt.OnlineOrderService;
import fyp.canteen.fypapi.service.ordermgmt.OrderFoodMappingService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnlineOrder;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.foodmgmt.FoodMenuRequestPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OnlineOrderRequestPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OnlineOrderResponsePojo;
import fyp.canteen.fypcore.pojo.pagination.OnlineOrderPaginationRequestPojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.Map;

@RestController
@RequestMapping("/online-order")
@Tag(name = ModuleNameConstants.ONLINE_ORDER)
public class OnlineOrderController extends BaseController {
    private final OnlineOrderService onlineOrderService;
    private final OrderFoodMappingService orderFoodMappingService;
    private final OrderFoodMappingMapper orderFoodMappingMapper;

    public OnlineOrderController(OnlineOrderService onlineOrderService, OrderFoodMappingMapper orderFoodMappingMapper,
                                 OrderFoodMappingService orderFoodMappingService){
        this.onlineOrderService = onlineOrderService;
        this.orderFoodMappingMapper = orderFoodMappingMapper;
        this.orderFoodMappingService = orderFoodMappingService;
        this.moduleName = ModuleNameConstants.ONLINE_ORDER;
    }

    @PostMapping
    @Operation(summary = "Use this api to save/update food menu details", responses = {@ApiResponse(responseCode = "200")})
    public ResponseEntity<GlobalApiResponse> saveOnlineOrder(@RequestBody @Valid OnlineOrderRequestPojo requestPojo){
        OnlineOrderResponsePojo onlineOrder = onlineOrderService.saveOnlineOrder(requestPojo);
        onlineOrder.setOrderFoodDetails(orderFoodMappingMapper.getAllFoodDetailsByOrderId(onlineOrder.getId(),
                true));
        onlineOrder.setOrderCode(onlineOrder.getOrderCode().split(" ")[1]);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.SAVE, moduleName),
                CRUD.SAVE, onlineOrder));
    }

    @PostMapping("/paginated")
    @Operation(summary = "Use this api to paginated online order list", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getOnlineOrderPaginated(@RequestBody OnlineOrderPaginationRequestPojo requestPojo){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, onlineOrderService.getPaginatedOrderListByTime(requestPojo)));
    }

    @GetMapping("/user-orders")
    @Operation(summary = "Use this api to paginated online order list", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getOnlineOrderOfUser(){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, onlineOrderService.getUserOnlineOrder()));
    }

    @GetMapping("/make-onsite/{id}")
    @Operation(summary = "Use this api to paginated online order list", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> convertToOnsite(@PathVariable("id") Long id){
        onlineOrderService.convertOnlineToOnsite(id);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET,null));
    }

    @GetMapping("/summary/{fromTime}/{toTime}")
    @Operation(summary = "Use this api to paginated online order list", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> orderSummary(@PathVariable("fromTime")LocalTime fromTime, @PathVariable("toTime") LocalTime toTime){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET,onlineOrderService.getOrderSummary(fromTime, toTime)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Use this api to get single online order", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getOnlineOrderById(@PathVariable("id") Long id){
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, onlineOrderService.getOnlineOrderById(id)));
    }

    @DeleteMapping("/order-food/{id}")
    public ResponseEntity<GlobalApiResponse> deleteOrderedFood(@PathVariable("id") Long id){
        orderFoodMappingService.removeOrderFoodById(id);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, null));
    }
    
    
}
