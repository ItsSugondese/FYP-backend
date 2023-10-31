package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypcore.model.entity.ordermgmt.OnlineOrder;
import fyp.canteen.fypcore.model.entity.ordermgmt.OrderUserMapping;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodMappingRequestPojo;

import java.util.List;

public interface OrderFoodMappingService {

    void saveOrderFoodMapping(OrderFoodMappingRequestPojo requestPojo, OnlineOrder onlineOrder, OrderUserMapping orderUserMapping);

    void removeOrderFoodList(List<Long> ids);
}
