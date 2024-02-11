package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypcore.enums.OrderType;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnsiteOrder;
import fyp.canteen.fypcore.pojo.ordermgmt.OnsiteOrderRequestPojo;
import fyp.canteen.fypcore.pojo.pagination.OnsiteOrderPaginationRequestPojo;
import fyp.canteen.fypcore.pojo.pagination.OrderDetailsPaginationRequest;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;

public interface OnsiteOrderService {
    void saveOnsiteOrder(OnsiteOrderRequestPojo requestPojo, OrderType orderType);

    PaginationResponse getPaginatedOrderListByTime(OnsiteOrderPaginationRequestPojo requestPojo);
    PaginationResponse getPaginatedOrderHistoryDetails(OrderDetailsPaginationRequest requestPojo);

    OnsiteOrder findById(Long id);
}
