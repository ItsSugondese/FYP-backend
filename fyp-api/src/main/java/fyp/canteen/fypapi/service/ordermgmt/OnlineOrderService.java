package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypcore.model.entity.ordermgmt.OnlineOrder;
import fyp.canteen.fypcore.pojo.ordermgmt.OnlineOrderRequestPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OnlineOrderResponsePojo;
import fyp.canteen.fypcore.pojo.pagination.OnlineOrderPaginationRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;

import java.util.List;

public interface OnlineOrderService {

    OnlineOrderResponsePojo saveOnlineOrder(OnlineOrderRequestPojo requestPojo);

    PaginationResponse getPaginatedOrderListByTime(OnlineOrderPaginationRequestPojo requestPojo);
    List<OnlineOrderResponsePojo> getUserOnlineOrder();
    void  convertOnlineToOnsite(Long id);
    OnlineOrderResponsePojo getOnlineOrderById(Long id);

    OnlineOrder findById(Long id);
}
