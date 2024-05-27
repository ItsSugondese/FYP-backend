package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypcore.model.entity.ordermgmt.OnlineOrder;
import fyp.canteen.fypcore.pojo.ordermgmt.OnlineOrderRequestPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OnlineOrderResponsePojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodResponsePojo;
import fyp.canteen.fypcore.pojo.pagination.OnlineOrderPaginationRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface OnlineOrderService {

    OnlineOrderResponsePojo saveOnlineOrder(OnlineOrderRequestPojo requestPojo);

    PaginationResponse getPaginatedOrderListByTime(OnlineOrderPaginationRequestPojo requestPojo);
    List<OnlineOrderResponsePojo> getUserOnlineOrder();
    void  convertOnlineToOnsite(Long id, String code);
    OnlineOrderResponsePojo getOnlineOrderById(Long id);

    List<OrderFoodResponsePojo> getOrderSummary(LocalTime fromTime, LocalTime toTime);

    void deleteById(Long id);
    OnlineOrder findById(Long id);
}
