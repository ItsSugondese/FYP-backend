package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypapi.repository.ordermgmt.OrderUserMappingRepo;
import fyp.canteen.fypcore.enums.ApprovalStatus;
import fyp.canteen.fypcore.enums.DeliveryStatus;
import fyp.canteen.fypcore.enums.OrderType;
import fyp.canteen.fypcore.enums.PayStatus;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnlineOrder;
import fyp.canteen.fypcore.model.entity.ordermgmt.OrderUserMapping;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodMappingRequestPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderUserMappingRequestPojo;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import fyp.canteen.fypcore.utils.UserDataConfig;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderUserMappingServiceImpl implements OrderUserMappingService {
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
    private final OrderUserMappingRepo orderUserMappingRepo;
    private final UserDataConfig userDataConfig;
    private final OrderFoodMappingService orderFoodMappingService;

    @Transactional
    @Override
    public void saveOnsiteOrder(OrderUserMappingRequestPojo requestPojo, OrderType orderType) {
        OrderUserMapping orderUserMapping = new OrderUserMapping();

        if(requestPojo.getId() != null)
            orderUserMapping = orderUserMappingRepo.findById(requestPojo.getId()).orElse(orderUserMapping);

        if(orderUserMapping.getId() == null && requestPojo.getFoodOrderList().isEmpty() && orderType.equals(OrderType.ONSITE))
            throw new AppException("User must order something when making request");
        try {
            beanUtilsBean.copyProperties(orderUserMapping, requestPojo);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }

        orderUserMapping.setUser(User.builder().id(userDataConfig.userId()).build());
        if(orderType.equals(OrderType.ONLINE))
            orderUserMapping.setOnlineOrder(OnlineOrder.builder().id(requestPojo.getOnlineOrderId()).build());


        orderUserMapping.setOrderType(orderType);
        orderUserMapping.setPayStatus(PayStatus.UNPAID);
        orderUserMapping.setDeliveryStatus(DeliveryStatus.PENDING);
        orderUserMapping.setApprovalStatus(ApprovalStatus.PENDING);

        orderUserMapping = orderUserMappingRepo.save(orderUserMapping);

        if (!requestPojo.getFoodOrderList().isEmpty() || !requestPojo.getRemoveFoodId().isEmpty())
            orderFoodMappingService.saveOrderFoodMapping(OrderFoodMappingRequestPojo.builder()
                    .foodOrderList(requestPojo.getFoodOrderList())
                    .removeFoodId(requestPojo.getRemoveFoodId())
                    .build(), null, orderUserMapping);
    }
}
