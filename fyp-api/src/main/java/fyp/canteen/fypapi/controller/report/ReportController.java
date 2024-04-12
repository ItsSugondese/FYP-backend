package fyp.canteen.fypapi.controller.report;

import fyp.canteen.fypapi.service.dashboard.admin.AdminDashboardService;
import fyp.canteen.fypapi.service.dashboard.admin.ReportService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.MessageConstants;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.enums.RevenueFilterType;
import fyp.canteen.fypcore.generics.controller.BaseController;
import fyp.canteen.fypcore.pojo.GlobalApiResponse;
import fyp.canteen.fypcore.pojo.dashboard.RevenueDataRequestPojo;
import fyp.canteen.fypcore.pojo.dashboard.SalesDataRequestPojo;
import fyp.canteen.fypcore.pojo.usermgmt.UserFinanceDataPaginationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;


@RestController
@RequestMapping("/report")
@Tag(name = ModuleNameConstants.REPORT)
public class ReportController extends BaseController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
        this.moduleName = ModuleNameConstants.REPORT;
    }

    @GetMapping("/revenue/{fromDate}/{toDate}")
    @Operation(summary = "Use this api to paginated online order list", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getRevenueExcelReport(@PathVariable("fromDate") LocalDate fromDate, @PathVariable("toDate") LocalDate toDate,
                                                                  HttpServletResponse response){
//        reportService.generateRevenueReport(response,fromDate, toDate);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, null));
    }
    @PostMapping("/revenue")
    @Operation(summary = "Use this api to paginated online order list", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getRevenueExcelReport(@RequestBody RevenueDataRequestPojo requestPojo,
                                                                   HttpServletResponse response){
        reportService.generateRevenueReport(response,requestPojo);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, null));
    }

    @GetMapping("/finance/{fromDate}/{toDate}")
    @Operation(summary = "Use this api to paginated online order list", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getUserData(@PathVariable("fromDate") LocalDate fromDate, @PathVariable("toDate") LocalDate toDate,
                                                                  HttpServletResponse response){
        UserFinanceDataPaginationRequest paginationRequest = new UserFinanceDataPaginationRequest();
        paginationRequest.setFromDate(fromDate);
        paginationRequest.setToDate(toDate);
        reportService.generateUserFinanceData(response, paginationRequest);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, null));
    }

    @PostMapping("/finance")
    @Operation(summary = "Use this api to paginated online order list", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getPostUserData(@RequestBody UserFinanceDataPaginationRequest paginationRequest,
                                                                  HttpServletResponse response){
        reportService.generateUserFinanceData(response, paginationRequest);
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, null));
    }

    @GetMapping("/revenue/{fromDate}/{toDate}/{type}")
    @Operation(summary = "Use this api to paginated online order list", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public ResponseEntity<GlobalApiResponse> getSalesDataReport(@PathVariable("fromDate") LocalDate fromDate, @PathVariable("toDate") LocalDate toDate,
                                                               @PathVariable("type") RevenueFilterType filter,
                                                                  HttpServletResponse response){

        reportService.generateSalesReport(response, SalesDataRequestPojo.builder()
                        .filterType(filter)
                        .limit(null)
                        .foodType(null)
                        .fromDate(fromDate)
                        .toDate(toDate)
                .build());
        return ResponseEntity.ok(successResponse(Message.crud(MessageConstants.GET, moduleName),
                CRUD.GET, null));
    }

    @PostMapping("/sales")
    @Operation(summary = "Use this api to paginated online order list", responses = {@ApiResponse(responseCode = "200",
            content = {@Content(array =
            @ArraySchema(schema = @Schema(implementation = Map.class)))}, description = "This api will save the details of Bank,Bank Type and Network")})
    public void getPostSalesDataReport(@RequestBody SalesDataRequestPojo requestPojo,
                                                                  HttpServletResponse response){

        reportService.generateSalesReport(response, requestPojo);
    }
}
