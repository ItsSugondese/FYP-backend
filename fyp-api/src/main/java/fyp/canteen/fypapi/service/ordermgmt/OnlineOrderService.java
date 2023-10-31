package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypcore.pojo.ordermgmt.OnlineOrderRequestPojo;

public interface OnlineOrderService {

    void saveOnlineOrder(OnlineOrderRequestPojo requestPojo);
}
