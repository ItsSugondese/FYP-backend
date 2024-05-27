package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypapi.mapper.ordermgmt.OrderFoodMappingMapper;
import fyp.canteen.fypapi.repository.foodmgmt.FoodMenuRepo;
import fyp.canteen.fypapi.repository.inventory.InventoryMenuMappingRepo;
import fyp.canteen.fypapi.repository.notification.NotificationRepo;
import fyp.canteen.fypapi.repository.ordermgmt.OnsiteOrderRepo;
import fyp.canteen.fypapi.repository.payment.UserPaymentDetailsRepo;
import fyp.canteen.fypapi.service.dashboard.admin.AdminDashboardService;
import fyp.canteen.fypapi.service.notification.NotificationService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.constants.NotificationMessageConstants;
import fyp.canteen.fypcore.enums.ApprovalStatus;
import fyp.canteen.fypcore.enums.PayStatus;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import fyp.canteen.fypcore.model.entity.inventory.InventoryMenuMapping;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnsiteOrder;
import fyp.canteen.fypcore.model.entity.ordermgmt.OrderFoodMapping;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.model.notification.Notification;
import fyp.canteen.fypcore.pojo.ordermgmt.OnsiteOrderRequestPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodMappingRequestPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodResponsePojo;
import fyp.canteen.fypcore.pojo.pagination.OnsiteOrderOfUserPaginationRequestPojo;
import fyp.canteen.fypcore.pojo.pagination.OnsiteOrderPaginationRequestPojo;
import fyp.canteen.fypcore.pojo.pagination.OrderDetailsPaginationRequest;
import fyp.canteen.fypcore.pojo.pagination.OrderHistoryPaginationRequest;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import fyp.canteen.fypcore.utils.UserDataConfig;
import fyp.canteen.fypcore.utils.pagination.CustomPaginationHandler;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private final NotificationRepo notificationRepo;
    private final InventoryMenuMappingRepo inventoryMenuMappingRepo;
    private final FoodMenuRepo foodMenuRepo;
    private final NotificationService notificationService;
    private final AdminDashboardService adminDashboardService;

    @Transactional
    @Override
    public OnsiteOrder saveOnsiteOrder(OnsiteOrderRequestPojo requestPojo) {
        OnsiteOrder onsiteOrder = new OnsiteOrder();

        if(requestPojo.getId() != null)
            onsiteOrder = onsiteOrderRepo.findById(requestPojo.getId()).orElse(onsiteOrder);

        if(onsiteOrder.getId() == null && requestPojo.getFoodOrderList().isEmpty())
            throw new AppException("User must order something when making request");
        try {
            beanUtilsBean.copyProperties(onsiteOrder, requestPojo);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }

        if(requestPojo.getUserId() == null)
            onsiteOrder.setUser(User.builder().id(userDataConfig.userId()).build());
        else
            onsiteOrder.setUser(User.builder().id(requestPojo.getUserId()).build());


        onsiteOrder.setApprovalStatus(ApprovalStatus.PENDING);
        OnsiteOrder savedOnsite = onsiteOrderRepo.save(onsiteOrder);

        if (!requestPojo.getFoodOrderList().isEmpty() || !requestPojo.getRemoveFoodId().isEmpty())
            orderFoodMappingService.saveOrderFoodMapping(OrderFoodMappingRequestPojo.builder()
                    .foodOrderList(requestPojo.getFoodOrderList())
                    .removeFoodId(requestPojo.getRemoveFoodId())
                    .build(), null, savedOnsite);

        adminDashboardService.pingOrderSocket();
        return onsiteOrder;
    }

    @Override
    public PaginationResponse getPaginatedOrderListByTime(OnsiteOrderPaginationRequestPojo requestPojo) {
//        PaginationResponse response =  customPaginationHandler.getPaginatedData(onsiteOrderRepo.getOnsiteOrderPaginated(
//                requestPojo.getMinuteRange(),
//                requestPojo.getOnsiteOrderFilter().toString(), requestPojo.getName(), Pageable.unpaged()
//        ));

        LocalDateTime fromDT = LocalDateTime.now().minusMinutes(requestPojo.getMinuteRange());
        LocalDateTime toDT = LocalDateTime.now();
        PaginationResponse response =  customPaginationHandler.getPaginatedData(onsiteOrderRepo.getOnsiteOrderPaginated(
                fromDT,
                requestPojo.getOnsiteOrderFilter().toString(), requestPojo.getName(),
                toDT,
                null,
                Pageable.unpaged()
        ));

        response.setContent(response.getContent().stream().map(
                e -> {

                    Map<String, Object> map = new HashMap<>(e);
                    map.put("orderFoodDetails", orderFoodMappingMapper.getAllFoodDetailsByOrderId( ((Long)e.get("id")),
                            false));
                    map.put("remainingAmount", userPaymentDetailsRepo.getTotalRemainingAmountWithUnpaidToPayOfUserByUserId(Long.parseLong(String.valueOf(e.get("userId")))));
                    return map;
                }
        ).collect(Collectors.toList()));


        return response;
    }

    @Override
    public PaginationResponse getPaginatedOrderHistoryOfAllUsers(OrderHistoryPaginationRequest requestPojo) {
        LocalDateTime fromDT = LocalDateTime.of(requestPojo.getFromDate(), LocalTime.MIDNIGHT);
        LocalDateTime toDT = LocalDateTime.of(requestPojo.getToDate(), LocalTime.MAX);
        PaginationResponse response =  customPaginationHandler.getPaginatedData(onsiteOrderRepo.getOnsiteOrderPaginated(
                fromDT,
                "ALL", requestPojo.getName(),
                toDT,
                requestPojo.getPayStatus() == null? null : requestPojo.getPayStatus().toString(),
                requestPojo.getPageable()
        ));

        response.setContent(response.getContent().stream().map(
                e -> {

                    Map<String, Object> map = new HashMap<>(e);
                    map.put("orderFoodDetails", orderFoodMappingMapper.getAllFoodDetailsByOrderId( ((Long)e.get("id")),
                            false));
                    map.put("remainingAmount", userPaymentDetailsRepo.getTotalRemainingAmountWithUnpaidToPayOfUserByUserId(Long.parseLong(String.valueOf(e.get("userId")))));
                    return map;
                }
        ).collect(Collectors.toList()));


        return response;
    }

    @Override
    public PaginationResponse getPaginatedOrderOfUser(OnsiteOrderOfUserPaginationRequestPojo requestPojo) {
        PaginationResponse response =  customPaginationHandler.getPaginatedData(onsiteOrderRepo.getOnsiteOrderOfUserPaginated(
                 requestPojo.getUserId(),
                requestPojo.getPayStatus() == null ? null : requestPojo.getPayStatus().toString(),
                requestPojo.getPageable()
        ));
//        PaginationResponse response =  customPaginationHandler.getPaginatedData(onsiteOrderRepo.getOnsiteOrderPaginated(
//                requestPojo.getTimeRange(), requestPojo.isRead(), requestPojo.getPageable()
//        ));
        response.setContent(response.getContent().stream().map(
                e -> {

                    Map<String, Object> map = new HashMap<>(e);
                    map.put("orderFoodDetails", orderFoodMappingMapper.getAllFoodDetailsByOrderId( ((Long)e.get("id")),
                            true));

                    double amount;
                    PayStatus payStatus =  PayStatus.valueOf(String.valueOf(e.get("payStatus")).toUpperCase());
                    if(payStatus == PayStatus.UNPAID)
                        amount = Double.parseDouble(String.valueOf(e.get("totalPrice")));
                    else if(payStatus == PayStatus.PAID)
                        amount = 0D;
                    else
                        amount = userPaymentDetailsRepo.getTotalRemainingAmountOfOrderByOrderId(Long.parseLong(String.valueOf(e.get("id"))));

                    map.put("remainingAmount", amount);
                    return map;
                }
        ).collect(Collectors.toList()));


        return response;
    }

    @Override
    public void updateOrderStatus(Long id, ApprovalStatus status) {
        OnsiteOrder onsiteOrder = findById(id);

        List<OrderFoodMapping> foodMappings = orderFoodMappingService.getAllOrderedFoodByOnsiteOrder(onsiteOrder);
        foodMappings.forEach(
                e -> {
                    FoodMenu menu = foodMenuRepo.findById(e.getFoodMenu().getId()).get();
                    if(menu.getIsAuto()){
                       InventoryMenuMapping menuMapping= inventoryMenuMappingRepo.findLatestLog(e.getFoodMenu().getId());

                        if(menuMapping.getStock() < (e.getQuantity()+ menuMapping.getRemainingQuantity())){
                            menuMapping.setStock(menuMapping.getRemainingQuantity() + e.getQuantity());
                            menuMapping.setRemainingQuantity(menuMapping.getStock());
                        }else {
                            menuMapping.setRemainingQuantity(menuMapping.getRemainingQuantity() + e.getQuantity());
                        }

                        inventoryMenuMappingRepo.save(menuMapping);

                        if(!menu.getIsAvailableToday()){
                            menu.setIsAvailableToday(true);
                            foodMenuRepo.save(menu);
                        }
                    }
                }
        );
        onsiteOrder.setApprovalStatus(status);
        onsiteOrderRepo.save(onsiteOrder);

        adminDashboardService.sendRevenueDataSocket();
        adminDashboardService.pingOrderSocket();

    }

    @Override
    public PaginationResponse getPaginatedOrderHistoryDetails(OrderDetailsPaginationRequest requestPojo) {
        PaginationResponse response =  customPaginationHandler.getPaginatedData(onsiteOrderRepo.getUserOrderPaginated(requestPojo.getFromDate(),
                requestPojo.getToDate(),
                userDataConfig.userId(),
                requestPojo.getPayStatus() == null? null : requestPojo.getPayStatus().name(),
                requestPojo.getToday(),
                requestPojo.getToday()? Pageable.unpaged():  requestPojo.getPageable()));

        setPaginationResponseWithExtraResponseDataForUserHistory(response);
        return  response;
    }


    @Override
    @Transactional
    public void markOnsiteOrderAsRead(Long orderId) {
        OnsiteOrder onsiteOrder = findById(orderId);
        onsiteOrder.setMarkAsRead(true);
        onsiteOrderRepo.save(onsiteOrder);

        notificationRepo.save(Notification.builder()
                        .user(onsiteOrder.getUser())
                        .message(NotificationMessageConstants.MARKED_AS_READ)
                        .isSeen(false)
                .build());

        notificationService.newNotificationsSocket(onsiteOrder.getUser().getId());



    }



    @Override
    public void setToPaid(Long orderId) {
        OnsiteOrder onsiteOrder = findById(orderId);
        onsiteOrder.setPayStatus(PayStatus.PAID);
        onsiteOrderRepo.save(onsiteOrder);
    }

    @Override
    public OnsiteOrder findById(Long id) {
        return onsiteOrderRepo.findById(id).orElseThrow(() -> new AppException(Message.idNotFound(ModuleNameConstants.ONSITE_ORDER)));
    }


    private void setPaginationResponseWithExtraResponseDataForUserHistory(PaginationResponse response) {
        response.setContent(response.getContent().stream().map(
                e -> {
                    Map<String, Object> map = new HashMap<>(e);
                    PayStatus payStatus =  PayStatus.valueOf(String.valueOf(e.get("payStatus")).toUpperCase());
                    double totalPrice = Double.parseDouble(String.valueOf(e.get("totalPrice")));

                    map.put("payStatus", payStatus.getText());
                    if(payStatus == PayStatus.UNPAID)
                        map.put("remainingAmount", totalPrice);
                    else if(payStatus == PayStatus.PAID) {
                        map.put("remainingAmount", 0D);
                    }
                    else {
                        map.put("remainingAmount", userPaymentDetailsRepo.getTotalRemainingAmountOfOrderByOrderId(Long.parseLong(String.valueOf(e.get("id")))));
                    }
                    List<OrderFoodResponsePojo> orderFoodResponsePojos = orderFoodMappingMapper.getAllFoodDetailsByOrderId(((Long) e.get("id")),
                            false);
                    map.put("orderFoodDetails", orderFoodResponsePojos);
                    return map;
                }
        ).collect(Collectors.toList()));
    }
}
