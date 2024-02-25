package fyp.canteen.fypapi.repository.ordermgmt;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnsiteOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public interface OnsiteOrderRepo extends GenericSoftDeleteRepository<OnsiteOrder, Long> {

    @Query(value = "  SELECT oo.id, oo.order_type as \"orderType\",  \n" +
            "  case when Extract(day from (current_timestamp - oo.created_date)) > 0 then  Extract(day from (current_timestamp - oo.created_date)) ||  ' days ' else '' end\n" +
            "  || \n" +
            "  case when Extract(hour from (current_timestamp - oo.created_date))>0 then Extract(hour from (current_timestamp - oo.created_date)) || ' hours ' else '' end \n" +
            "  ||\n" +
            "  case when Extract(minute from (current_timestamp - oo.created_date)) > 0 then Extract(minute from (current_timestamp - oo.created_date)) || ' min ' else '' end\n" +
            "  ||\n" +
            "  case when Extract(second from (current_timestamp - oo.created_date))>0 then  CAST(EXTRACT(SECOND FROM date_trunc('second', CURRENT_TIMESTAMP - oo.created_date)) AS INTEGER)  || ' sec' else '' end\n" +
            "  as \"orderedTime\",\n" +
            "  oo.approval_status as \"approvalStatus\", oo.total_price as \"totalPrice\", oo.user_id as \"userId\", \n" +
            "INITCAP(u.full_name) as \"fullName\", u.email, u.profile_path as \"profileUrl\", oo.mark_as_read as \"markAsRead\" \n" +
            "FROM onsite_order oo  \n" +
            "JOIN users u ON u.id = oo.user_id \n" +
            "where case when cast(?1 as date) is null then true else true end and \n" +
            "case when ?2 = 'PENDING' then oo.mark_as_read is false \n" +
            "   when ?2 = 'VIEWED' then oo.mark_as_read is true and oo.approval_status = 'PENDING' \n" +
            "   when ?2 = 'DELIVERED' then oo.approval_status = 'APPROVED'\n" +
            "   when ?2 = 'CANCELED' then oo.approval_status = 'REJECTED'\n" +
            "end and \n" +
            "case when ?3 = '-1' then true else u.full_name ilike concat('%',?3,'%') end",
countQuery = "Select count(*) from (\n" +
        " SELECT oo.id, oo.order_type as \"orderType\",  \n" +
        "  case when Extract(day from (current_timestamp - oo.created_date)) > 0 then  Extract(day from (current_timestamp - oo.created_date)) ||  ' days ' else '' end\n" +
        "  || \n" +
        "  case when Extract(hour from (current_timestamp - oo.created_date))>0 then Extract(hour from (current_timestamp - oo.created_date)) || ' hours ' else '' end \n" +
        "  ||\n" +
        "  case when Extract(minute from (current_timestamp - oo.created_date)) > 0 then Extract(minute from (current_timestamp - oo.created_date)) || ' min ' else '' end\n" +
        "  ||\n" +
        "  case when Extract(second from (current_timestamp - oo.created_date))>0 then  CAST(EXTRACT(SECOND FROM date_trunc('second', CURRENT_TIMESTAMP - oo.created_date)) AS INTEGER)  || ' sec' else '' end\n" +
        "  as \"orderedTime\",\n" +
        "  oo.approval_status as \"approvalStatus\", oo.total_price as \"totalPrice\", oo.user_id as \"userId\", \n" +
        "INITCAP(u.full_name) as \"fullName\", u.email, u.profile_path as \"profileUrl\" \n" +
        "FROM onsite_order oo  \n" +
        "JOIN users u ON u.id = oo.user_id \n" +
        "where case when cast(?1 as date) is null then true else true end and \n" +
        "case when ?2 = 'PENDING' then oo.mark_as_read is false \n" +
        "   when ?2 = 'VIEWED' then oo.mark_as_read is true and oo.approval_status = 'PENDING' \n" +
        "   when ?2 = 'DELIVERED' then oo.approval_status = 'APPROVED'\n" +
        "   when ?2 = 'CANCELED' then oo.approval_status = 'REJECTED'\n" +
        "end and  \n" +
        "case when ?3 = '-1' then true else u.full_name ilike concat('%',?3,'%') end) foo"
//            "where oo.pay_status = 'UNPAID' "
            , nativeQuery = true)
    Page<Map<String, Object>> getOnsiteOrderPaginated(LocalTime timeRange, String showStatus, String name, Pageable pageable);

    @Query(value = "select oo.id, 'ONLINE_ORDER' as \"orderType\", u.profile_path as \"profileUrl\", to_char(oo.created_date, 'YYYY-MM-DD HH:MI AM') as date, oo.arrival_time as \"arrivalTime\" from online_order oo\n" +
            "join users u on u.id = oo.user_id \n" +
            "where oo.is_active and oo.user_id = ?3\n" +
            "and \n" +
            "  oo.created_date between ?1 and ?2\n" +
            "union \n" +
            "select onsite.id, 'ONSITE_ORDER' as \"orderType\", u.profile_path as \"profileUrl\", to_char(onsite.created_date, 'YYYY-MM-DD HH:MI AM') as date, null as \"arrivalTime\"  from onsite_order onsite join users u on u.id = onsite.user_id where onsite.is_active and onsite.user_id = ?3 and\n" +
            " onsite.created_date between ?1 and ?2",
            countQuery = "select count(*) from (\n" +
                    "select oo.id, 'ONLINE_ORDER' as orderType from online_order oo\n" +
                    "join users u on u.id = oo.user_id \n" +
                    "where oo.is_active and oo.user_id = ?3\n" +
                    "and \n" +
                    "  oo.created_date between ?1 and ?2\n" +
                    "union \n" +
                    "select onsite.id, 'ONSITE_ORDER' as orderType  from onsite_order onsite where onsite.is_active and onsite.user_id = ?3 and\n" +
                    " onsite.created_date between ?1 and ?2) foo",
            nativeQuery = true)
    Page<Map<String, Object>> getUserOrderPaginated(LocalDate fromDate, LocalDate toDate, Long userId, Pageable pageable);
}
