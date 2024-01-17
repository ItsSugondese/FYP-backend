package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypcore.model.entity.ordermgmt.OnlineOrder;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnsiteOrder;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodMappingRequestPojo;

import java.util.List;

public interface OrderFoodMappingService {

    void saveOrderFoodMapping(OrderFoodMappingRequestPojo requestPojo, OnlineOrder onlineOrder, OnsiteOrder onsiteOrder);

    void removeOrderFoodList(List<Long> ids);
}
