package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypcore.model.entity.ordermgmt.OnlineOrder;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnsiteOrder;
import fyp.canteen.fypcore.model.entity.ordermgmt.OrderFoodMapping;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodMappingRequestPojo;

import java.util.List;

public interface OrderFoodMappingService {

    void saveOrderFoodMapping(OrderFoodMappingRequestPojo requestPojo, OnlineOrder onlineOrder, OnsiteOrder onsiteOrder);

    List<OrderFoodMapping> getAllOrderedFoodByOnlineOrder(OnlineOrder onlineOrder);
    List<OrderFoodMapping> getAllOrderedFoodByOnsiteOrder(OnsiteOrder order);
    OrderFoodMapping findById(Long id);
    void removeOrderFoodList(List<Long> ids);
    void removeOrderFoodById(Long id);
}
