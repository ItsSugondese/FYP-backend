package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypapi.mapper.ordermgmt.OrderFoodMappingMapper;
import fyp.canteen.fypapi.repository.ordermgmt.OnsiteOrderRepo;
import fyp.canteen.fypapi.repository.payment.UserPaymentDetailsRepo;
import fyp.canteen.fypapi.service.food.FoodMenuService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.ApprovalStatus;
import fyp.canteen.fypcore.enums.DeliveryStatus;
import fyp.canteen.fypcore.enums.OrderType;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnlineOrder;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnsiteOrder;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.pojo.ordermgmt.OnsiteOrderRequestPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodMappingRequestPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodResponsePojo;
import fyp.canteen.fypcore.pojo.pagination.OnsiteOrderPaginationRequestPojo;
import fyp.canteen.fypcore.pojo.pagination.OrderDetailsPaginationRequest;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import fyp.canteen.fypcore.utils.UserDataConfig;
import fyp.canteen.fypcore.utils.pagination.CustomPaginationHandler;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OnsiteOrderServiceImpl implements OnsiteOrderService {
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
    private final OnsiteOrderRepo onsiteOrderRepo;
    private final UserDataConfig userDataConfig;
    private final OrderFoodMappingService orderFoodMappingService;
    private final CustomPaginationHandler customPaginationHandler;
    private final OrderFoodMappingMapper orderFoodMappingMapper;
    private final UserPaymentDetailsRepo userPaymentDetailsRepo;

    @Transactional
    @Override
    public void saveOnsiteOrder(OnsiteOrderRequestPojo requestPojo, OrderType orderType) {
        OnsiteOrder onsiteOrder = new OnsiteOrder();

        if(requestPojo.getId() != null)
            onsiteOrder = onsiteOrderRepo.findById(requestPojo.getId()).orElse(onsiteOrder);

        if(onsiteOrder.getId() == null && requestPojo.getFoodOrderList().isEmpty() && orderType.equals(OrderType.ONSITE))
            throw new AppException("User must order something when making request");
        try {
            beanUtilsBean.copyProperties(onsiteOrder, requestPojo);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }

        onsiteOrder.setUser(User.builder().id(userDataConfig.userId()).build());
        if(orderType.equals(OrderType.ONLINE)) {
//            onsiteOrder.setOnlineOrder(OnlineOrder.builder().id(requestPojo.getOnlineOrderId()).build());
        }

        onsiteOrder.setApprovalStatus(ApprovalStatus.PENDING);
        onsiteOrder = onsiteOrderRepo.save(onsiteOrder);

        if (!requestPojo.getFoodOrderList().isEmpty() || !requestPojo.getRemoveFoodId().isEmpty())
            orderFoodMappingService.saveOrderFoodMapping(OrderFoodMappingRequestPojo.builder()
                    .foodOrderList(requestPojo.getFoodOrderList())
                    .removeFoodId(requestPojo.getRemoveFoodId())
                    .build(), null, onsiteOrder);
    }

    @Override
    public PaginationResponse getPaginatedOrderListByTime(OnsiteOrderPaginationRequestPojo requestPojo) {
        PaginationResponse response =  customPaginationHandler.getPaginatedData(onsiteOrderRepo.getOnsiteOrderPaginated(
                null, requestPojo.getOnsiteOrderFilter().toString(), requestPojo.getName(), Pageable.unpaged()
        ));
//        PaginationResponse response =  customPaginationHandler.getPaginatedData(onsiteOrderRepo.getOnsiteOrderPaginated(
//                requestPojo.getTimeRange(), requestPojo.isRead(), requestPojo.getPageable()
//        ));
        response.setContent(response.getContent().stream().map(
                e -> {

                    Map<String, Object> map = new HashMap<>(e);
                    map.put("orderFoodDetails", orderFoodMappingMapper.getAllFoodDetailsByOrderId( ((Long)e.get("id")),
                            true));
                    map.put("remainingAmount", userPaymentDetailsRepo.getTotalRemainingAmountToPayOfUserByUserId(Long.parseLong(String.valueOf(e.get("userId")))));
                    return map;
                }
        ).collect(Collectors.toList()));


        return response;
    }

    @Override
    public void updateOrderStatus(Long id, ApprovalStatus status) {
        OnsiteOrder onsiteOrder = findById(id);

        onsiteOrder.setApprovalStatus(status);
        onsiteOrderRepo.save(onsiteOrder);
    }

    @Override
    public PaginationResponse getPaginatedOrderHistoryDetails(OrderDetailsPaginationRequest requestPojo) {
        PaginationResponse response =  customPaginationHandler.getPaginatedData(onsiteOrderRepo.getUserOrderPaginated(requestPojo.getFromDate(),
                requestPojo.getToDate(),
                userDataConfig.userId(), requestPojo.getPageable()));

        response.setContent(response.getContent().stream().map(
                e -> {
                    Map<String, Object> map = new HashMap<>(e);
                    List<OrderFoodResponsePojo> orderFoodResponsePojos = orderFoodMappingMapper.getAllFoodDetailsByOrderId(((Long) e.get("id")),
                            true);
                    map.put("orderFoodDetails", orderFoodResponsePojos);
                    return map;
                }
        ).collect(Collectors.toList()));
        return  response;
    }

    @Override
    public void markOnsiteOrderAsRead(Long orderId) {
        OnsiteOrder onsiteOrder = findById(orderId);
        onsiteOrder.setMarkAsRead(true);
        onsiteOrderRepo.save(onsiteOrder);
    }

    @Override
    public OnsiteOrder findById(Long id) {
        return onsiteOrderRepo.findById(id).orElseThrow(() -> new AppException(Message.idNotFound(ModuleNameConstants.ONSITE_ORDER)));
    }
}
