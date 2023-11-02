package fyp.canteen.fypapi.mapper.ordermgmt;

import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodResponsePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderFoodMappingMapper {

    @Select("select  ofm.id as id,  ofm.quantity as quantity,fm.\"name\" as food_name, fm.\"cost\" as cost, \n" +
            "(fm.\"cost\" * ofm.quantity) as totalPrice\n" +
            "from order_food_mapping ofm join food_menu fm on ofm.food_id  = fm.id \n" +
            "where case when #{isOnlineOrder} = true then ofm.online_order_id = #{id} else ofm.order_user_mapping_id = #{id} end")
    List<OrderFoodResponsePojo> getAllFoodDetailsByOrderId(@Param("id") Long id,
                                                           @Param("isOnlineOrder") boolean isOnlineOrder);
}
