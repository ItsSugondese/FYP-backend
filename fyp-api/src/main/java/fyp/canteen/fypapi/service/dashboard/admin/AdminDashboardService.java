package fyp.canteen.fypapi.service.dashboard.admin;

import fyp.canteen.fypcore.pojo.dashboard.*;
import fyp.canteen.fypcore.pojo.dashboard.data.*;

public interface AdminDashboardService {

    SalesDataPojo getSalesData(SalesDataRequestPojo requestPojo);

    UserDataPojo getUserData(UserDataRequestPojo requestPojo);

    OrderDataPojo getOrderData(OrderDataRequestPojo requestPojo);

    RevenueDataPojo getRevenueData(RevenueDataRequestPojo requestPojo);
    FoodMenuDataPojo getFoodMenuDataData(FoodMenuDataRequestPojo requestPojo);
    TableDataPojo getTableData(TableDataRequestPojo requestPojo);
}
