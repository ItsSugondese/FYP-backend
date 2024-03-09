package fyp.canteen.fypapi.controller.dashboard;

import fyp.canteen.fypapi.service.dashboard.admin.AdminDashboardService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.dashboard.*;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dashboard")
@Tag(name = ModuleNameConstants.ADMIN_DASHBOARD)
public class AdminDashboardController extends BaseController {
    private final AdminDashboardService adminDashboardService;
    public AdminDashboardController(AdminDashboardService adminDashboardService){
        this.adminDashboardService = adminDashboardService;
        this.moduleName = ModuleNameConstants.ADMIN_DASHBOARD;
    }


    @PostMapping("/sales-data")
    @Operation(summary = "Get All notification of particular member")
    public ResponseEntity<GlobalApiResponse> getSalesData(@RequestBody SalesDataRequestPojo request) {
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET,
                adminDashboardService.getSalesData(request)));
    }

    @PostMapping("/users-data")
    @Operation(summary = "Get All notification of particular member")
    public ResponseEntity<GlobalApiResponse> getUsersData(@RequestBody UserDataRequestPojo request) {
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET,
                adminDashboardService.getUserData(request)));
    }

    @PostMapping("/order-data")
    @Operation(summary = "Get All notification of particular member")
    public ResponseEntity<GlobalApiResponse> getOrderData(@RequestBody OrderDataRequestPojo request) {
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET,
                adminDashboardService.getOrderData(request)));
    }

    @PostMapping("/revenue-data")
    @Operation(summary = "Get All notification of particular member")
    public ResponseEntity<GlobalApiResponse> getRevenueData(@RequestBody RevenueDataRequestPojo request) {
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET,
                adminDashboardService.getRevenueData(request)));
    }

    @PostMapping("/food-menu-data")
    @Operation(summary = "Get All notification of particular member")
    public ResponseEntity<GlobalApiResponse> getFoodMenuData(@RequestBody FoodMenuDataRequestPojo request) {
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET,
                adminDashboardService.getFoodMenuDataData(request)));
    }

}
