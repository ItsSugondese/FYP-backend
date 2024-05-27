package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypapi.repository.foodmgmt.FoodMenuRepo;
import fyp.canteen.fypapi.repository.inventory.InventoryMenuMappingRepo;
import fyp.canteen.fypapi.repository.ordermgmt.OnlineOrderRepo;
import fyp.canteen.fypapi.repository.ordermgmt.OrderFoodMappingRepo;
import fyp.canteen.fypapi.service.food.FoodMenuService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import fyp.canteen.fypcore.model.entity.inventory.InventoryMenuMapping;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnlineOrder;
import fyp.canteen.fypcore.model.entity.ordermgmt.OrderFoodMapping;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnsiteOrder;
import fyp.canteen.fypcore.pojo.foodmgmt.ToggleAvailableTodayRequestPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodMappingRequestPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodResponsePojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderFoodMappingServiceImpl implements OrderFoodMappingService{

    private final OrderFoodMappingRepo orderFoodMappingRepo;
    private final OnlineOrderRepo onlineOrderRepo;
    private final FoodMenuService foodMenuService;
    private final InventoryMenuMappingRepo inventoryMenuMappingRepo;
    private final FoodMenuRepo foodMenuRepo;

    @Override
    @Transactional
    public void saveOrderFoodMapping(OrderFoodMappingRequestPojo requestPojo, OnlineOrder onlineOrder, OnsiteOrder onsiteOrder) {
        orderFoodMappingRepo.saveAllAndFlush(Optional.ofNullable(requestPojo.getFoodOrderList()).orElse(new ArrayList<>())
                .stream().map(
                        e -> {
                            OrderFoodMapping orderFoodMapping = new OrderFoodMapping();
                            if(e.getId() != null)
                                orderFoodMapping = orderFoodMappingRepo.findById(e.getId()).orElseThrow(() ->  new AppException("Didn't find respective food data to edit"));
//                            OrderFoodMapping orderFoodMapping = orderFoodMappingRepo.findByFoodIdAndOrderId(e.getFoodId(), onlineOrder.getId(), orderUserMapping.getId());


                            FoodMenu foodMenu = foodMenuService.findById(e.getFoodId());
                            List<InventoryMenuMapping> menus = null;
                            int totalRemain = 0;
                            if(foodMenu.getIsAuto() ){
                                menus = inventoryMenuMappingRepo.findAllInStockByFoodMenuId(foodMenu.getId());

                                 totalRemain = menus.stream().mapToInt(val -> val.getRemainingQuantity()).sum();

                                if(totalRemain < e.getQuantity())
                                    throw new AppException("Only " + totalRemain + " of " + foodMenu.getName() + " is left.");
                            }
                            if(Boolean.FALSE.equals(foodMenu.getIsAvailableToday()))
                                throw new AppException(foodMenu.getName() + " isn't available to order.");
                            orderFoodMapping.setFoodMenu(foodMenu);
                            orderFoodMapping.setQuantity(e.getQuantity());
                            orderFoodMapping.setTotalCost(e.getQuantity() * foodMenu.getCost());
                            if(onlineOrder != null) {
                                if((onsiteOrder != null || (orderFoodMapping.getId()!= null && !Objects.equals(orderFoodMapping.getOnlineOrder().getId(), onlineOrder.getId()))))
                                    throw new AppException("The order you're trying to update is  made by someone else");
                                orderFoodMapping.setOnlineOrder(onlineOrder);
                            }

                            if(onsiteOrder != null) {
//                                if((onlineOrder!= null || (orderFoodMapping.getId()!= null && !Objects.equals(orderFoodMapping.getOnsiteOrder().getId(), onsiteOrder.getId()))))
//                                    throw new AppException("The order you're trying to update is  made by someone else");
                                orderFoodMapping.setOnsiteOrder(onsiteOrder);
                            }

                            if(menus != null  && onsiteOrder != null){
                                int remainingQuantity = e.getQuantity();
                                for(InventoryMenuMapping menu: menus){
                                    if(menu.getRemainingQuantity() == remainingQuantity){
                                        menu.setRemainingQuantity(0);
                                        inventoryMenuMappingRepo.save(menu);
                                        break;
                                    }else if(menu.getRemainingQuantity() > remainingQuantity){
                                        menu.setRemainingQuantity(menu.getRemainingQuantity() - remainingQuantity);
                                        break;
                                    }else{
                                        remainingQuantity -= menu.getRemainingQuantity();
                                        menu.setRemainingQuantity(0);
                                        inventoryMenuMappingRepo.save(menu);
                                    }
                                }

                                if(totalRemain == e.getQuantity()){
                                    foodMenuService.toggleAvailability(ToggleAvailableTodayRequestPojo.builder()
                                            .foodId(foodMenu.getId())
                                            .status(false)
                                            .build());
                                }
                            }
                            return  orderFoodMapping;
                        }
                ).collect(Collectors.toList())
        );

        if(!requestPojo.getRemoveFoodId().isEmpty())
            removeOrderFoodList(requestPojo.getRemoveFoodId());



    }

    @Override
    public void removeOrderFoodList(List<Long> ids) {
        ids.forEach(orderFoodMappingRepo::deleteById);
    }

    @Override
    public OrderFoodMapping findById(Long id) {
        return orderFoodMappingRepo.findById(id)
                .orElseThrow(() -> new AppException(Message.idNotFound(ModuleNameConstants.ORDER_FOOD_MAPPING)));
    }

    @Override
    @Transactional
    public void removeOrderFoodById(Long id) {
        OrderFoodMapping orderFoodMapping = findById(id);
        OnlineOrder onlineOrder = orderFoodMapping.getOnlineOrder();



        orderFoodMappingRepo.deleteById(id);

        onlineOrder.setTotalPrice(onlineOrder.getTotalPrice() - orderFoodMapping.getTotalCost());
        onlineOrderRepo.save(onlineOrder);
    }


    @Override
    public List<OrderFoodMapping> getAllOrderedFoodByOnlineOrder(OnlineOrder onlineOrder) {
        return orderFoodMappingRepo.findAllByOnlineOrder(onlineOrder);
    }

    @Override
    public List<OrderFoodMapping> getAllOrderedFoodByOnsiteOrder(OnsiteOrder order) {
        return orderFoodMappingRepo.findAllByOnsiteOrder(order);
    }
}
