package fyp.canteen.fypapi.mapper.ordermgmt;

import fyp.canteen.fypcore.pojo.dashboard.data.OnlineOrderDataPojo;
import fyp.canteen.fypcore.pojo.dashboard.data.OnsiteOrderDataPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OnlineOrderResponsePojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OrderFoodResponsePojo;
import org.apache.ibatis.annotations.*;

import java.time.LocalTime;
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
            "to_char(oo.arrival_time, 'HH:mi am' ) as \"arrivalTime\", oo.arrival_time as \"arrivalTime24\", oo.approval_status as \"approvalStatus\", \n" +
            "    (string_to_array(oo.order_code , ' ')) [2] as \"orderCode\", oo.user_id as \"userId\", INITCAP(u.full_name) as \"fullName\", u.email \n" +
            "   from online_order oo join users u on u.id = oo.user_id  where oo.is_active and \n" +
            "cast(oo.created_date as date) = current_date and\n" +
            "oo.approval_status = 'PENDING' and oo.user_id = #{userId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "orderFoodDetails", column = "id",
                    many = @Many(select = "fyp.canteen.fypapi.mapper.ordermgmt.OrderFoodMappingMapper.getAllOnlineFoodDetailsByOrderId"))
    })
    List<OnlineOrderResponsePojo> getTodayOrderOfUserByUserId(Long userId);


    @Select("SELECT \n" +
            "count(*) as total,\n" +
            "coalesce(sum(case when oo.approval_status = 'DELIVERED' then 1 else 0 end), 0) as approved,\n" +
            "coalesce(sum(case when oo.approval_status = 'PENDING' then 1 else 0 end),0) as pending\n" +
            "FROM online_order  oo\n" +
            "where cast(oo.created_date as date) = current_date and case when  oo.arrival_time + make_interval(mins := #{minDifference}) < oo.arrival_time  then (cast('00:00' as time) - interval '1 millisecond') else oo.arrival_time + make_interval(mins := #{minDifference}) end > current_time")
    OnlineOrderDataPojo getOnlineOrderStatistics(@Param("minDifference") Integer minDifference);

    @Select("select child.id, child.\"name\" as foodName, sum(child.quantity) as quantity, child.\"photoId\" from (\n" +
            "select oo.id from online_order oo where oo.approval_status = 'PENDING' and oo.is_active\n" +
            "and oo.created_date::date = current_date  and \n" +
            "oo.arrival_time between cast(#{fromTime} as time) and cast(#{toTime} as time)) parent\n" +
            "join lateral (select fm.id, fm.\"name\", ofm.quantity, \n" +
            "(select fmp.id  from food_menu_picture fmp where fmp.is_active and fmp.food_menu_id = fm.id) as \"photoId\" \n" +
            "  from order_food_mapping ofm  \n" +
            "join food_menu fm on fm.id = ofm.food_id \n" +
            "where ofm.online_order_id = parent.id \n" +
            ") child on true\n" +
            "group by child.id, child.\"name\", child.\"photoId\"")
    List<OrderFoodResponsePojo> getOnlineOrderSummary(@Param("fromTime") LocalTime fromTime,
                                                 @Param("toTime") LocalTime toTime);
}
