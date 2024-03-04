package fyp.canteen.fypapi.service.ordermgmt;

import fyp.canteen.fypapi.repository.ordermgmt.OrderFoodMappingRepo;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnlineOrder;
import fyp.canteen.fypcore.model.entity.ordermgmt.OrderFoodMapping;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnsiteOrder;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodMappingRequestPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodResponsePojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderFoodMappingServiceImpl implements OrderFoodMappingService{

    private final OrderFoodMappingRepo orderFoodMappingRepo;
    @Override
    public void saveOrderFoodMapping(OrderFoodMappingRequestPojo requestPojo, OnlineOrder onlineOrder, OnsiteOrder onsiteOrder) {
        orderFoodMappingRepo.saveAllAndFlush(Optional.ofNullable(requestPojo.getFoodOrderList()).orElse(new ArrayList<>())
                .stream().map(
                        e -> {
                            OrderFoodMapping orderFoodMapping = new OrderFoodMapping();
                            if(e.getId() != null)
                                orderFoodMapping = orderFoodMappingRepo.findById(e.getId()).orElse(new OrderFoodMapping());
//                            OrderFoodMapping orderFoodMapping = orderFoodMappingRepo.findByFoodIdAndOrderId(e.getFoodId(), onlineOrder.getId(), orderUserMapping.getId());


                            orderFoodMapping.setFoodMenu(FoodMenu.builder().id(e.getFoodId()).build());
                            orderFoodMapping.setQuantity(e.getQuantity());
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
    public void removeOrderFoodById(Long id) {
        orderFoodMappingRepo.deleteById(id);
    }

    @Override
    public List<OrderFoodMapping> getAllOrderedFoodByOnlineOrder(OnlineOrder onlineOrder) {
        return orderFoodMappingRepo.findAllByOnlineOrder(onlineOrder);
    }
}
