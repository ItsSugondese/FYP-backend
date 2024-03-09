package fyp.canteen.fypapi.mapper.ordermgmt;

import fyp.canteen.fypcore.model.entity.ordermgmt.OnsiteOrder;
import fyp.canteen.fypcore.pojo.dashboard.data.OnsiteOrderDataPojo;
import fyp.canteen.fypcore.pojo.ordermgmt.OnsiteOrderResponsePojo;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

@Mapper
public interface OnsiteOrderMapper {

    @Select("select *, oo.user_id as userId from onsite_order oo where oo.id = #{id}")
    @Results({
            @Result(property = "user", column = "userId",
                    one = @One(select = "fyp.canteen.fypapi.mapper.usermgmt.UserDetailMapper.getById"))
    })
    Optional<OnsiteOrder> getById(@Param("id") Long id);

    @Select("  SELECT oo.id,  INITCAP(oo.pay_status) as \"payStatus\", oo.pay_status as \"payStatusCheck\", \n" +
            "  case \n" +
            "\t  when \n" +
            "  \tExtract(day from (current_timestamp - oo.ordered_time)) > 0 then  Extract(day from (current_timestamp - oo.ordered_time)) ||  ' days ' ||\n" +
            "\tExtract(hour from (current_timestamp - oo.ordered_time)) || ' hours'  \n" +
            "  \t when\n" +
            "  \t\t Extract(hour from (current_timestamp - oo.ordered_time))>0 then Extract(hour from (current_timestamp - oo.ordered_time)) || ' hours ' || \n" +
            "  \t\tExtract(minute from (current_timestamp - oo.ordered_time)) || ' min'\n" +
            "  \twhen \n" +
            "  Extract(minute from (current_timestamp - oo.ordered_time)) > 0 then Extract(minute from (current_timestamp - oo.ordered_time)) || ' min ' \n" +
            "else\n" +
            " CAST(EXTRACT(SECOND FROM date_trunc('second', CURRENT_TIMESTAMP - oo.ordered_time)) AS INTEGER)  || ' sec' \n" +
            "  end\n" +
            "  as \"orderedTime\",\n" +
            "  oo.approval_status as \"approvalStatus\", oo.total_price as \"totalPrice\", oo.user_id as \"userId\", \n" +
            "INITCAP(u.full_name) as \"fullName\", u.email, u.profile_path as \"profileUrl\", oo.mark_as_read as \"markAsRead\", \n" +
            "case\n" +
            "   when oo.approval_status = 'CANCELED' then 'canceled' \n" +
            "   when  oo.pay_status = 'PAID' then 'Paid'\n" +
            "   when  oo.pay_status = 'PARTIAL_PAID' then 'Partial Paid'\n" +
            "   when oo.mark_as_read is false then 'Pending'\n" +
            "   when oo.mark_as_read is true and oo.approval_status = 'PENDING' then 'Viewed'\n" +
            "   when oo.approval_status = 'DELIVERED' and oo.pay_status = 'UNPAID' then 'Delivered'\n" +
            "end as \"orderStatus\", \n" +
            "case when oo.has_feedback then null\n" +
            "when oo.approval_status = 'DELIVERED' and oo.approval_status  <> 'CANCELED' and \n" +
            "oo.has_feedback is false and cast(oo.created_date as date) = current_date then true \n" +
            "else false end as \"feedbackStatus\" \n" +
            "FROM onsite_order oo  \n" +
            "JOIN users u ON u.id = oo.user_id \n" +
            "where oo.is_active and oo.user_id = #{id} and cast(oo.created_date as date) in ((current_date - interval '1 day'), current_date)")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "orderFoodDetails", column = "id",
                    many = @Many(select = "fyp.canteen.fypapi.mapper.ordermgmt.OrderFoodMappingMapper.getAllOnsiteFoodDetailsByOrderId"))
    })
    List<OnsiteOrderResponsePojo> getTodayOnsiteUserOrders(Long id);

    @Select("SELECT count(*) as total, " +
            "coalesce(sum(case when oo.approval_status = 'DELIVERED' then 1 else 0 end),0) as delivered, " +
            "coalesce(sum(case when oo.approval_status = 'PENDING' then 1 else 0 end), 0) as pending, " +
            "coalesce(sum(case when oo.approval_status = 'CANCELED' then 1 else 0 end), 0) as canceled " +
            "FROM onsite_order oo " +
            "WHERE oo.created_date between current_timestamp - make_interval(mins := #{minDifference})  and CURRENT_TIMESTAMP")
    OnsiteOrderDataPojo getOnsiteOrderStatistics(@Param("minDifference") Integer minDifference);

}
