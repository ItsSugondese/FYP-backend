package fyp.canteen.fypapi.service.dashboard.admin;

import fyp.canteen.fypapi.mapper.payment.UserPaymentDetailsMapper;
import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypcore.enums.RevenueFilterType;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.pojo.dashboard.RevenueDataRequestPojo;
import fyp.canteen.fypcore.pojo.dashboard.SalesDataRequestPojo;
import fyp.canteen.fypcore.pojo.dashboard.data.RevenueDataPojo;
import fyp.canteen.fypcore.pojo.dashboard.data.SalesDataPojo;
import fyp.canteen.fypcore.pojo.usermgmt.UserFinanceDataPaginationRequest;
import fyp.canteen.fypcore.utils.excel.ExcelGenerator;
import fyp.canteen.fypcore.utils.pagination.CustomPaginationHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final AdminDashboardService adminDashboardService;
    private final ExcelGenerator excelGenerator;
    private final UserPaymentDetailsMapper userPaymentDetailsMapper;
    private final CustomPaginationHandler customPaginationHandler;
    private final UserRepo userRepo;

    @Override
    public void generateRevenueReport(HttpServletResponse response, RevenueDataRequestPojo requestPojo) {
        RevenueDataPojo data = adminDashboardService.getRevenueData(requestPojo);


        Map<String, Object> revenueData = new LinkedHashMap<>();

        revenueData.put("Total Revenue", "Rs. " + data.getRevenue());
        revenueData.put("Total Paid", "Rs. " + data.getPaidAmount());
        revenueData.put("Left To Receive", "Rs. " + data.getLeftToPay());
        revenueData.put("Total Order Delivered", data.getDeliveredOrder().toString());


        try {
            excelGenerator.export(response, List.of(revenueData),
                    headerGenerator("Revenue", requestPojo.getFromDate(), requestPojo.getToDate()));
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public void generateSalesReport(HttpServletResponse response, SalesDataRequestPojo requestPojo) {
        List<SalesDataPojo.FoodSalesData> foodSalesData = userPaymentDetailsMapper.getSalesData(
                requestPojo.getFilterType().toString(),
                requestPojo.getFromDate(),
                requestPojo.getToDate(),
                requestPojo.getFoodType() == null ? null : requestPojo.getFoodType().toString().toUpperCase()
        );
        SalesDataPojo salesData = adminDashboardService.getSalesData(requestPojo);


        List<Map<String, Object>> salesDataPojos = new ArrayList<>();

        AtomicReference<Integer> serialNo = new AtomicReference<>(1);
        AtomicReference<Integer> totalQuantity = new AtomicReference<>(0);
        AtomicReference<Double> totalPrice = new AtomicReference<>(0D);
        foodSalesData.forEach(
                e -> {
                    Map<String, Object> menuSales = new LinkedHashMap<>();

                    menuSales.put("Serial No.", serialNo.getAndSet(serialNo.get() + 1));
                    menuSales.put("Menu", e.getName());
                    menuSales.put("Price Per Piece", "Rs. " + e.getSalesIncome()/e.getQuantity());
                    menuSales.put("Quantity Sold", e.getQuantity());
                    menuSales.put("Amount", "Rs. " + e.getSalesIncome());
                    salesDataPojos.add(menuSales);

                    totalQuantity.set(totalQuantity.get() + e.getQuantity());
                    totalPrice.set(totalPrice.get() + e.getSalesIncome());
                }
        );


        Map<String, Object> map  = new LinkedHashMap<>();
        map.put("Serial No.", "");
        map.put("Menu", "");
        map.put("Price Per Piece", "");
        map.put("Quantity Sold", totalQuantity.get());
        map.put("Amount", "Rs. " + totalPrice.get());
        salesDataPojos.add(map);

        try {
            excelGenerator.export(response, salesDataPojos, headerGenerator("Sales", requestPojo.getFromDate(), requestPojo.getToDate()));
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public void generateUserFinanceData(HttpServletResponse response, UserFinanceDataPaginationRequest paginationRequest) {
        List<Map<String, Object>> data = customPaginationHandler.getPaginatedData(userRepo.getAllUserFinanceData(paginationRequest.getFromDate(),
                paginationRequest.getToDate(), paginationRequest.getName(), Pageable.unpaged())).getContent();

        List<Map<String, Object>> userDataPojos = new ArrayList<>();


        data.forEach(
                e -> {
                    Map<String, Object> menuSales = new LinkedHashMap<>();

                    menuSales.put("Serial No.", String.valueOf(e.get("sno")));
                    menuSales.put("Username", String.valueOf(e.get("fullName")));
                    menuSales.put("Total Paid", "Rs. " + e.get("totalPaid"));
                    menuSales.put("Total Due", "Rs. " +  e.get("dueAmount"));
                    menuSales.put("Total Transaction", "Rs. " +  e.get("totalTransaction"));
                    userDataPojos.add(menuSales);
                }
        );

        try {
            excelGenerator.export(response, userDataPojos, headerGenerator("User expense", paginationRequest.getFromDate(), paginationRequest.getToDate()));
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }
    }

    private String headerGenerator(String title, LocalDate fromDate, LocalDate toDate){
        return title + " data between " + fromDate + " and " + toDate;
    }
}
