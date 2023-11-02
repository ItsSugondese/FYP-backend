package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypapi.mapper.ordermgmt.OrderFoodMappingMapper;
import fyp.canteen.fypapi.repository.ordermgmt.OnlineOrderRepo;
import fyp.canteen.fypcore.enums.ApprovalStatus;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnlineOrder;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.pojo.ordermgmt.OnlineOrderRequestPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodMappingRequestPojo;
import fyp.canteen.fypcore.pojo.pagination.OnlineOrderPaginationRequestPojo;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import fyp.canteen.fypcore.utils.UserDataConfig;
import fyp.canteen.fypcore.utils.pagination.CustomPaginationHandler;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OnlineOrderServiceImpl implements OnlineOrderService {
    private final OnlineOrderRepo onlineOrderRepo;
    private final OrderFoodMappingService orderFoodMappingService;
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
    private final UserDataConfig userDataConfig;
    private final CustomPaginationHandler customPaginationHandler;
    private final OrderFoodMappingMapper orderFoodMappingMapper;

    @Override
    @Transactional
    public void saveOnlineOrder(OnlineOrderRequestPojo requestPojo) {
        OnlineOrder onlineOrder = new OnlineOrder();

        if (requestPojo.getId() != null)
            onlineOrder = onlineOrderRepo.findById(requestPojo.getId()).orElse(onlineOrder);

        if (onlineOrder.getId() == null) {
            onlineOrder.setOrderCode(LocalDate.now() + " " + (onlineOrderRepo.noOfOrders(LocalDate.now()) + 1));
            validationWhenIdIsNull(requestPojo);
        }
        try {
            beanUtilsBean.copyProperties(onlineOrder, requestPojo);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }

        if(onlineOrder.getFromTime().isAfter(onlineOrder.getToTime()))
            throw new AppException("From time must not be ahead of to time");

        if (onlineOrder.getApprovalStatus() == null)
            onlineOrder.setApprovalStatus(ApprovalStatus.PENDING);

        onlineOrder.setUser(User.builder().id(userDataConfig.userId()).build());

        onlineOrder = onlineOrderRepo.save(onlineOrder);

        if (!requestPojo.getFoodOrderList().isEmpty() || !requestPojo.getRemoveFoodId().isEmpty())
            orderFoodMappingService.saveOrderFoodMapping(OrderFoodMappingRequestPojo.builder()
                    .foodOrderList(requestPojo.getFoodOrderList())
                    .removeFoodId(requestPojo.getRemoveFoodId())
                    .build(), onlineOrder, null);
    }

    @Override
    public PaginationResponse getPaginatedOrderListByTime(OnlineOrderPaginationRequestPojo requestPojo) {
        PaginationResponse response = customPaginationHandler.getPaginatedData(onlineOrderRepo.getOnlineOrderPaginated(
                requestPojo.getFromTime(), requestPojo.getToTime(), requestPojo.getPageable()));

        response.setContent(response.getContent().stream().map(
                e -> {

                    Map<String, Object> map = new HashMap<>(e);
                   map.put("orderFoodDetails", orderFoodMappingMapper.getAllFoodDetailsByOrderId( ((Long)e.get("id")),
                           true));
                    return map;
                }
        ).collect(Collectors.toList()));

        return response;
    }

    private void validationWhenIdIsNull(OnlineOrderRequestPojo requestPojo){
        if (requestPojo.getFoodOrderList().isEmpty())
            throw new AppException("Selecting food is compulsory when making orders.");

        if(requestPojo.getFromTime() == null || requestPojo.getToTime() == null)
            throw new AppException("Arrival time range must be selected");
    }
}
