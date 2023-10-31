package fyp.canteen.fypapi.repository.ordermgmt;

import fyp.canteen.fypcore.model.entity.ordermgmt.OrderFoodMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderFoodMappingRepo extends JpaRepository<OrderFoodMapping, Long> {

    @Query(value = "select * from order_food_mapping ofm where ofm.food_id = ?1 and \n" +
            "case when ?2=1 then ofm.online_order_id = ?2 else ofm.order_user_mapping_id = ?3 end", nativeQuery = true)
    OrderFoodMapping findByFoodIdAndOrderId(Long foodId, Long onlineOrderId, Long orderUserMappingId);
}
