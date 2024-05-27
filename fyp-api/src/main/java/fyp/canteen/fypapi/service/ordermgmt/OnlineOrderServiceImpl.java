package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypapi.mapper.ordermgmt.OnlineOrderMapper;
import fyp.canteen.fypapi.mapper.ordermgmt.OrderFoodMappingMapper;
import fyp.canteen.fypapi.repository.ordermgmt.OnlineOrderRepo;
import fyp.canteen.fypapi.repository.ordermgmt.OrderFoodMappingRepo;
import fyp.canteen.fypapi.service.dashboard.admin.AdminDashboardService;
import fyp.canteen.fypapi.service.table.TableService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.ApprovalStatus;
import fyp.canteen.fypcore.enums.OrderType;
import fyp.canteen.fypcore.enums.PayStatus;
import fyp.canteen.fypcore.enums.UserType;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnlineOrder;
import fyp.canteen.fypcore.model.entity.ordermgmt.OrderFoodMapping;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.pojo.ordermgmt.*;
import fyp.canteen.fypcore.pojo.pagination.OnlineOrderPaginationRequestPojo;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import fyp.canteen.fypcore.utils.UserDataConfig;
import fyp.canteen.fypcore.utils.pagination.CustomPaginationHandler;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private final OnlineOrderMapper onlineOrderMapper;
    private final OnsiteOrderService onsiteOrderService;
    private final OrderFoodMappingRepo orderFoodMappingRepo;
    private final TableService tableService;
    private final AdminDashboardService adminDashboardService;

    @Override
    @Transactional
    public OnlineOrderResponsePojo saveOnlineOrder(OnlineOrderRequestPojo requestPojo) {
        OnlineOrder onlineOrder = new OnlineOrder();

        if (requestPojo.getId() != null)
            onlineOrder = onlineOrderRepo.findById(requestPojo.getId()).orElse(onlineOrder);

        if (onlineOrder.getId() == null) {
            onlineOrder.setOrderCode(LocalDate.now() + " " + (onlineOrderRepo.noOfOrders(LocalDate.now()) + 1));
            onlineOrder.setUser(User.builder().id(userDataConfig.userId()).build());
            validationWhenIdIsNull(requestPojo);
            validation();
        }
        try {
            beanUtilsBean.copyProperties(onlineOrder, requestPojo);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }

        if (onlineOrder.getApprovalStatus() == null)
            onlineOrder.setApprovalStatus(ApprovalStatus.PENDING);


        onlineOrder = onlineOrderRepo.save(onlineOrder);

        if (!requestPojo.getFoodOrderList().isEmpty() || !requestPojo.getRemoveFoodId().isEmpty())
            orderFoodMappingService.saveOrderFoodMapping(OrderFoodMappingRequestPojo.builder()
                    .foodOrderList(requestPojo.getFoodOrderList())
                    .removeFoodId(requestPojo.getRemoveFoodId())
                    .build(), onlineOrder, null);


//         Integer.parseInt((onlineOrder.getOrderCode().split(" "))[1]);

        return OnlineOrderResponsePojo.builder()
                .id(onlineOrder.getId())
                .orderCode(onlineOrder.getOrderCode())
                .arrivalTime(onlineOrder.getArrivalTime().format(DateTimeFormatter.ofPattern("hh:mm a")))
                .profileUrl(onlineOrder.getUser().getProfilePath())
                .totalPrice(onlineOrder.getTotalPrice())
                .userId(onlineOrder.getUser().getId())
                .fullName(onlineOrder.getUser().getFullName())
                .email(onlineOrder.getUser().getEmail())
                .build();
    }

    @Override
    public PaginationResponse getPaginatedOrderListByTime(OnlineOrderPaginationRequestPojo requestPojo) {
        PaginationResponse response = customPaginationHandler.getPaginatedData(onlineOrderRepo.getOnlineOrderPaginated(
                requestPojo.getMinDifference(), requestPojo.getName(),Pageable.unpaged()));


        response.setContent(response.getContent().stream().map(
                e -> {

                    Map<String, Object> map = new HashMap<>(e);
                    map.put("orderFoodDetails", orderFoodMappingMapper.getAllFoodDetailsByOrderId(((Long) e.get("id")),
                            true));
                    return map;
                }
        ).collect(Collectors.toList()));

        return response;
    }

    @Override
    @Transactional
    public void convertOnlineToOnsite(Long id, String code) {
        OnlineOrder onlineOrder = findById(id);
        Integer tableNumber = null;
        List<OrderFoodMapping> orderFoodMappingList =  orderFoodMappingService.getAllOrderedFoodByOnlineOrder(onlineOrder);

        if(orderFoodMappingList.isEmpty())
            throw new AppException("No Food Is ordered");

        if(userDataConfig.userType().equals(UserType.USER) || userDataConfig.userType().equals(UserType.EXTERNAL_USER)){
            if(!tableService.verifyOnsite(code))
               throw new AppException("Invalid Qr Used for verification");

            tableNumber = Integer.parseInt(code.split(":")[1]);
        }
        onlineOrder.setApprovalStatus(ApprovalStatus.DELIVERED);
        onlineOrder = onlineOrderRepo.saveAndFlush(onlineOrder);

        onsiteOrderService.saveOnsiteOrder(OnsiteOrderRequestPojo.builder()
                        .foodOrderList(orderFoodMappingList.stream().map(
                                e -> FoodOrderRequestPojo.builder()
                                        .quantity(e.getQuantity())
                                        .foodId(e.getFoodMenu().getId())
                                        .id(e.getId())
                                        .build()
                        ).collect(Collectors.toList()))
                        .payStatus(PayStatus.UNPAID)
                        .fromOnline(true)
                        .removeFoodId(new ArrayList<>())
                        .tableNumber(tableNumber)
                        .orderedTime(onlineOrder.getCreatedDate().toLocalDateTime())
                        .totalPrice(onlineOrder.getTotalPrice())
                        .userId(onlineOrder.getUser().getId())
                .build());

        adminDashboardService.pingOrderSocket();
    }

    @Override
    public List<OrderFoodResponsePojo> getOrderSummary(LocalTime fromTime, LocalTime toTime) {
        return onlineOrderMapper.getOnlineOrderSummary(fromTime, toTime);
    }

    @Override
    public OnlineOrderResponsePojo getOnlineOrderById(Long id) {
//        return onlineOrderMapper.getOnlineOrderById(id).orElseThrow(() -> new AppException(Message.idNotFound(ModuleNameConstants.ONLINE_ORDER)));
        return null;
    }

    @Override
    public List<OnlineOrderResponsePojo> getUserOnlineOrder() {
        return onlineOrderMapper.getTodayOrderOfUserByUserId(userDataConfig.userId());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        orderFoodMappingRepo.deleteAllByOnlineOrderId(id);
        onlineOrderRepo.deleteCompletely(id);
    }

    @Override
    public OnlineOrder findById(Long id) {
        return onlineOrderRepo.findById(id).orElseThrow(() -> new AppException(Message.idNotFound(ModuleNameConstants.ONLINE_ORDER)));
    }

    private void validationWhenIdIsNull(OnlineOrderRequestPojo requestPojo) {
        if (requestPojo.getFoodOrderList().isEmpty())
            throw new AppException("Selecting food is compulsory when making orders.");
    }



    private void validation(){
        List<OnlineOrderResponsePojo> orders = getUserOnlineOrder();
        if(orders.stream().filter(e-> e.getApprovalStatus().equals(ApprovalStatus.PENDING)).findFirst().isPresent())
            throw new AppException("Please edit existing order there is already one pending");

    }
}
