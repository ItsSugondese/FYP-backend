package fyp.canteen.fypapi.mapper.ordermgmt;

import fyp.canteen.fypcore.pojo.ordermgmt.OnlineOrderResponsePojo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface OnlineOrderMapper {

//    @Select("select oo.id, u.profile_path as \"profileUrl\",  oo.order_code , oo.total_price as \"totalPrice\",\n" +
//            "to_char(oo.arrival_time, 'HH:mi am' ) as \"arrivalTime\", \n" +
//            "    (string_to_array(oo.order_code , ' ')) [2] as \"orderCode\", oo.user_id as \"userId\", INITCAP(u.full_name) as \"fullName\", u.email \n" +
//            "   from online_order oo join users u on u.id = oo.user_id  where oo.is_active and oo.id = #{id}")
//    @Results({
//            @Result(property = "id", column = "id")
//            @Result(property = "orderFoodDetails", column = "id"
//                    one = @One(select = "fyp.canteen.fypapi.mapper.usermgmt.UserDetailMapper.getById"))
//    })
//    Optional<OnlineOrderResponsePojo> getOnlineOrderById(Long id);

    @Select("   select oo.id, u.profile_path as \"profileUrl\",  oo.order_code , oo.total_price as \"totalPrice\",\n" +
            "to_char(oo.arrival_time, 'HH:mi am' ) as \"arrivalTime\", \n" +
            "    (string_to_array(oo.order_code , ' ')) [2] as \"orderCode\", oo.user_id as \"userId\", INITCAP(u.full_name) as \"fullName\", u.email \n" +
            "   from online_order oo join users u on u.id = oo.user_id  where oo.is_active and \n" +
//            "cast(oo.created_date as date) = current_date and\n" +
            "oo.approval_status = 'PENDING' and oo.user_id = #{userId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "orderFoodDetails", column = "id",
                    many = @Many(select = "fyp.canteen.fypapi.mapper.ordermgmt.OrderFoodMappingMapper.getAllOnlineFoodDetailsByOrderId"))
    })
    List<OnlineOrderResponsePojo> getTodayOrderOfUserByUserId(Long userId);
}
