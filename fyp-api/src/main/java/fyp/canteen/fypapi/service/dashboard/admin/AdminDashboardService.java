package fyp.canteen.fypapi.service.dashboard.admin;

import fyp.canteen.fypcore.pojo.dashboard.SalesDataRequestPojo;
import fyp.canteen.fypcore.pojo.dashboard.data.SalesDataPojo;

public interface AdminDashboardService {

    SalesDataPojo getSalesData(SalesDataRequestPojo requestPojo);
}
