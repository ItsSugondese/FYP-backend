package fyp.canteen.fypapi.mapper.ordermgmt;

import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodResponsePojo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderFoodMappingMapper {

    @Select("select  ofm.id as id, fm.id as \"foodId\",  ofm.quantity as quantity,fm.\"name\" as food_name, fm.\"cost\" as cost, \n" +
            "(fm.\"cost\" * ofm.quantity) as totalPrice, \n" +
            "(select fmp.id  from food_menu_picture fmp where fmp.is_active and fmp.food_menu_id = ofm.food_id) as photoId\n" +
            "from order_food_mapping ofm join food_menu fm on ofm.food_id  = fm.id \n" +
            "where case when #{isOnlineOrder} = true then ofm.online_order_id = #{id} else ofm.onsite_order_id = #{id} end")
    @Results({
            @Result(property = "foodId", column = "foodId"),
            @Result(property = "foodMenu", column = "foodId",
            one = @One(select = "fyp.canteen.fypapi.mapper.foodmgmt.FoodMenuMapper.getFoodMenuById"))
    })
    List<OrderFoodResponsePojo> getAllFoodDetailsByOrderId(@Param("id") Long id,
                                                           @Param("isOnlineOrder") boolean isOnlineOrder);

    @Select("select  ofm.id as id, fm.id as \"foodId\",  ofm.quantity as quantity,fm.\"name\" as food_name, fm.\"cost\" as cost, \n" +
            "(fm.\"cost\" * ofm.quantity) as totalPrice, \n" +
            "(select fmp.id  from food_menu_picture fmp where fmp.is_active and fmp.food_menu_id = ofm.food_id) as photoId\n" +
            "from order_food_mapping ofm join food_menu fm on ofm.food_id  = fm.id and \n" +
            "ofm.online_order_id = #{id} ")
    @Results({
            @Result(property = "foodId", column = "foodId"),
            @Result(property = "foodMenu", column = "foodId",
            one = @One(select = "fyp.canteen.fypapi.mapper.foodmgmt.FoodMenuMapper.getFoodMenuById"))
    })
    List<OrderFoodResponsePojo> getAllOnlineFoodDetailsByOrderId(@Param("id") Long id);

    @Select("select  ofm.id as id, fm.id as \"foodId\",  ofm.quantity as quantity,fm.\"name\" as food_name, fm.\"cost\" as cost, \n" +
            "(fm.\"cost\" * ofm.quantity) as totalPrice, \n" +
            "(select fmp.id  from food_menu_picture fmp where fmp.is_active and fmp.food_menu_id = ofm.food_id) as photoId\n" +
            "from order_food_mapping ofm join food_menu fm on ofm.food_id  = fm.id and \n" +
            "ofm.onsite_order_id = #{id} ")
    @Results({
            @Result(property = "foodId", column = "foodId"),
            @Result(property = "foodMenu", column = "foodId",
            one = @One(select = "fyp.canteen.fypapi.mapper.foodmgmt.FoodMenuMapper.getFoodMenuById"))
    })
    List<OrderFoodResponsePojo> getAllOnsiteFoodDetailsByOrderId(@Param("id") Long id);
}
