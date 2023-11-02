package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypcore.pojo.ordermgmt.OnlineOrderRequestPojo;
import fyp.canteen.fypcore.pojo.pagination.OnlineOrderPaginationRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;

public interface OnlineOrderService {

    void saveOnlineOrder(OnlineOrderRequestPojo requestPojo);

    PaginationResponse getPaginatedOrderListByTime(OnlineOrderPaginationRequestPojo requestPojo);
}
