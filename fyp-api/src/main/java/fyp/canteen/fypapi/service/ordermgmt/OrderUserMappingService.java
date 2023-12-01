package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypcore.enums.OrderType;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderUserMappingRequestPojo;

public interface OrderUserMappingService {
    void saveOnsiteOrder(OrderUserMappingRequestPojo requestPojo, OrderType orderType);
}
