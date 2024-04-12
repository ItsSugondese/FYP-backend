package fyp.canteen.fypapi.service.dashboard.admin;

import fyp.canteen.fypcore.pojo.dashboard.RevenueDataRequestPojo;
import fyp.canteen.fypcore.pojo.dashboard.SalesDataRequestPojo;
import fyp.canteen.fypcore.pojo.usermgmt.UserFinanceDataPaginationRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;

public interface ReportService {

    void generateRevenueReport(HttpServletResponse response, RevenueDataRequestPojo requestPojo);
    void generateSalesReport(HttpServletResponse response, SalesDataRequestPojo requestPojo);

    void generateUserFinanceData(HttpServletResponse response, UserFinanceDataPaginationRequest request);
}
