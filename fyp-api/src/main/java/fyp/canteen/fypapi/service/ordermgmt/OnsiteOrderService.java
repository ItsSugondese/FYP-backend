package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypcore.enums.ApprovalStatus;
import fyp.canteen.fypcore.enums.OrderType;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnsiteOrder;
import fyp.canteen.fypcore.pojo.ordermgmt.OnsiteOrderRequestPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OnsiteOrderResponsePojo;
import fyp.canteen.fypcore.pojo.pagination.OnsiteOrderOfUserPaginationRequestPojo;
import fyp.canteen.fypcore.pojo.pagination.OnsiteOrderPaginationRequestPojo;
import fyp.canteen.fypcore.pojo.pagination.OrderDetailsPaginationRequest;
import fyp.canteen.fypcore.pojo.pagination.OrderHistoryPaginationRequest;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;

import java.util.List;

public interface OnsiteOrderService {
    void saveOnsiteOrder(OnsiteOrderRequestPojo requestPojo);

    void updateOrderStatus(Long id, ApprovalStatus status);
    PaginationResponse getPaginatedOrderListByTime(OnsiteOrderPaginationRequestPojo requestPojo);
    PaginationResponse getPaginatedOrderHistoryOfAllUsers(OrderHistoryPaginationRequest requestPojo);
    PaginationResponse getPaginatedOrderOfUser(OnsiteOrderOfUserPaginationRequestPojo requestPojo);
    List<OnsiteOrderResponsePojo> userTodayOnsiteOrders();
    void markOnsiteOrderAsRead(Long orderId);
    PaginationResponse getPaginatedOrderHistoryDetails(OrderDetailsPaginationRequest requestPojo);

    void setToPaid(Long orderId);

    OnsiteOrder findById(Long id);
}
