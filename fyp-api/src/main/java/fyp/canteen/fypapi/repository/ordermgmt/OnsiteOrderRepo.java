package fyp.canteen.fypapi.repository.ordermgmt;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnsiteOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface OnsiteOrderRepo extends GenericSoftDeleteRepository<OnsiteOrder, Long> {

//    @Query(value = "  SELECT oo.id,  INITCAP(oo.pay_status) as \"payStatus\", oo.pay_status as \"payStatusCheck\", \n" +
//            "  case \n" +
//            "\t  when \n" +
//            "  \tExtract(day from (current_timestamp - oo.ordered_time)) > 0 then  Extract(day from (current_timestamp - oo.ordered_time)) ||  ' days ' ||\n" +
//            "\tExtract(hour from (current_timestamp - oo.ordered_time)) || ' hours'  \n" +
//            "  \t when\n" +
//            "  \t\t Extract(hour from (current_timestamp - oo.ordered_time))>0 then Extract(hour from (current_timestamp - oo.ordered_time)) || ' hours ' || \n" +
//            "  \t\tExtract(minute from (current_timestamp - oo.ordered_time)) || ' min'\n" +
//            "  \twhen \n" +
//            "  Extract(minute from (current_timestamp - oo.ordered_time)) > 0 then Extract(minute from (current_timestamp - oo.ordered_time)) || ' min ' \n" +
//            "else\n" +
//            " CAST(EXTRACT(SECOND FROM date_trunc('second', CURRENT_TIMESTAMP - oo.ordered_time)) AS INTEGER)  || ' sec' \n" +
//            "  end\n" +
//            "  as \"orderedTime\",\n" +
//            "  oo.approval_status as \"approvalStatus\", oo.total_price as \"totalPrice\", oo.user_id as \"userId\", \n" +
//            "INITCAP(u.full_name) as \"fullName\", u.email, u.profile_path as \"profileUrl\", oo.mark_as_read as \"markAsRead\", \n" +
//            "case\n" +
//            "   when oo.mark_as_read is false then 'Pending'\n" +
//            "   when oo.mark_as_read is true and oo.approval_status = 'PENDING' then 'Viewed'\n" +
//            "   when  oo.pay_status = 'PAID' then 'Paid'\n" +
//            "   when  oo.pay_status = 'PARTIAL_PAID' then 'Partial Paid'\n" +
//            "   when oo.approval_status = 'DELIVERED' and oo.pay_status = 'UNPAID' then 'Delivered'\n" +
//            "   when oo.approval_status = 'CANCELED' then 'canceled' \n" +
//            "end as \"orderStatus\"\n" +
//            "FROM onsite_order oo  \n" +
//            "JOIN users u ON u.id = oo.user_id \n" +
//            "where oo.created_date between current_timestamp - make_interval(0, 0, 0, 0, 0, ?1, 0) and CURRENT_TIMESTAMP and \n" +
//            "case when ?2 = 'PENDING' then oo.mark_as_read is false \n" +
//            "   when ?2 = 'VIEWED' then oo.mark_as_read is true and oo.approval_status = 'PENDING' \n" +
//            "   when ?2 = 'DELIVERED' then oo.approval_status = 'DELIVERED' and oo.pay_status = 'UNPAID' \n" +
//            "   when ?2 = 'CANCELED' then oo.approval_status = 'CANCELED'\n" +
//            "   when ?2 = 'PAID' then oo.approval_status = 'DELIVERED' and oo.pay_status <> 'UNPAID' \n" +
//            "end and \n" +
//            "case when ?3 = '-1' then true else u.full_name ilike concat('%',?3,'%') end",
//countQuery = "Select count(*) from (\n" +
//        " SELECT oo.id, \n" +
//        "  oo.approval_status as \"approvalStatus\", oo.total_price as \"totalPrice\", oo.user_id as \"userId\", \n" +
//        "INITCAP(u.full_name) as \"fullName\", u.email, u.profile_path as \"profileUrl\" \n" +
//        "FROM onsite_order oo  \n" +
//        "JOIN users u ON u.id = oo.user_id \n" +
//        "oo.created_date between current_timestamp - make_interval(0, 0, 0, 0, 0, ?1, 0) and CURRENT_TIMESTAMP and \n" +
//        "case when ?2 = 'PENDING' then oo.mark_as_read is false \n" +
//        "   when ?2 = 'VIEWED' then oo.mark_as_read is true and oo.approval_status = 'PENDING' \n" +
//        "   when ?2 = 'DELIVERED' then oo.approval_status = 'DELIVERED' and oo.pay_status = 'UNPAID'\n" +
//        "   when ?2 = 'CANCELED' then oo.approval_status = 'CANCELED'\n" +
//        "   when ?2 = 'PAID' then oo.approval_status = 'DELIVERED' and oo.pay_status <> 'UNPAID' \n" +
//        "end and  \n" +
//        "case when ?3 = '-1' then true else u.full_name ilike concat('%',?3,'%') end) foo"
////            "where oo.pay_status = 'UNPAID' "
//            , nativeQuery = true)
//    Page<Map<String, Object>> getOnsiteOrderPaginated(Integer minute, String showStatus, String name, Pageable pageable);

    @Query(value = "  SELECT oo.id, to_char(oo.created_date, 'YYYY-MM-DD HH:MI AM') as date, oo.table_number as \"tableNumber\",\n" +
            "INITCAP(oo.pay_status) as \"payStatus\", oo.pay_status as \"payStatusCheck\", \n" +
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
            "   when oo.mark_as_read is false then 'Pending'\n" +
            "   when oo.mark_as_read is true and oo.approval_status = 'PENDING' then 'Viewed'\n" +
            "   when  oo.pay_status = 'PAID' then 'Paid'\n" +
            "   when  oo.pay_status = 'PARTIAL_PAID' then 'Partial Paid'\n" +
            "   when oo.approval_status = 'DELIVERED' and oo.pay_status = 'UNPAID' then 'Delivered'\n" +
            "   when oo.approval_status = 'CANCELED' then 'canceled' \n" +
            "end as \"orderStatus\"\n" +
            "FROM onsite_order oo  \n" +
            "JOIN users u ON u.id = oo.user_id \n" +
            "where oo.created_date between ?1 and ?4 and \n" +
            "case when ?2 = 'PENDING' then oo.mark_as_read is false and oo.approval_status = 'PENDING' \n" +
            "   when ?2 = 'VIEWED' then oo.mark_as_read is true and oo.approval_status = 'PENDING' \n" +
            "   when ?2 = 'DELIVERED' then oo.approval_status = 'DELIVERED' and oo.pay_status = 'UNPAID' \n" +
            "   when ?2 = 'CANCELED' then oo.approval_status = 'CANCELED'\n" +
            "   when ?2 = 'PAID' then oo.approval_status = 'DELIVERED' and oo.pay_status <> 'UNPAID'  \n" +
            " else oo.approval_status = 'DELIVERED' \n" +
            "end and \n" +
            "case when ?3 = '-1' then true else u.full_name ilike concat('%',?3,'%') end and \n" +
            "case when ?5 is null then true else oo.pay_status = ?5 end \n" +
            "order by oo.ordered_time asc",
countQuery = "Select count(*) from (\n" +
        " SELECT oo.id,  INITCAP(oo.pay_status) as \"payStatus\", oo.pay_status as \"payStatusCheck\", \n" +
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
        "INITCAP(u.full_name) as \"fullName\", u.email, u.profile_path as \"profileUrl\", oo.mark_as_read as \"markAsRead\" \n" +
        "FROM onsite_order oo  \n" +
        "JOIN users u ON u.id = oo.user_id \n" +
        "where oo.created_date between ?1 and ?4 and \n" +
        "case when ?2 = 'PENDING' then oo.mark_as_read is false and oo.approval_status = 'PENDING'\n" +
        "   when ?2 = 'VIEWED' then oo.mark_as_read is true and oo.approval_status = 'PENDING' \n" +
        "   when ?2 = 'DELIVERED' then oo.approval_status = 'DELIVERED' and oo.pay_status = 'UNPAID' \n" +
        "   when ?2 = 'CANCELED' then oo.approval_status = 'CANCELED'\n" +
        "   when ?2 = 'PAID' then oo.approval_status = 'DELIVERED' and oo.pay_status <> 'UNPAID' \n" +
        "else oo.approval_status = 'DELIVERED' \n" +
        "end and \n" +
        "case when ?3 = '-1' then true else u.full_name ilike concat('%',?3,'%') end and \n" +
        "case when ?5 is null then true else oo.pay_status = ?5 end \n" +
        "order by oo.ordered_time asc) foo"
            , nativeQuery = true)
    Page<Map<String, Object>> getOnsiteOrderPaginated(LocalDateTime fromDT, String showStatus, String name, LocalDateTime toDT,
                                                      String payStatus,
                                                      Pageable pageable);



    @Query(value = "  SELECT oo.id,  INITCAP(oo.pay_status) as \"payStatus\", oo.pay_status as \"payStatusCheck\", \n" +
            "to_char(oo.ordered_time, 'YYYY-MM-DD HH:MI AM') as \"orderedTime\", \n" +
            "  oo.approval_status as \"approvalStatus\", oo.total_price as \"totalPrice\", oo.user_id as \"userId\", \n" +
            "INITCAP(u.full_name) as \"fullName\", u.email, u.profile_path as \"profileUrl\", oo.mark_as_read as \"markAsRead\" \n" +
            "FROM onsite_order oo  \n" +
            "JOIN users u ON u.id = oo.user_id \n" +
            "where u.id = ?1 and  oo.approval_status = 'DELIVERED' and  \n" +
            "case when ?2 = 'UNPAID' then oo.pay_status = 'UNPAID' \n" +
            "   when ?2 = 'PAID' then oo.pay_status = 'PAID' \n" +
            "   when ?2 = 'PARTIAL_PAID' then  oo.pay_status = 'PARTIAL_PAID' \n" +
            " else true\n" +
            "end",
countQuery = "select count(*) from (\n" +
        "  SELECT oo.id,  INITCAP(oo.pay_status) as \"payStatus\", oo.pay_status as \"payStatusCheck\", \n" +
        "oo.ordered_time as \"orderedTime\", \n" +
        "  oo.approval_status as \"approvalStatus\", oo.total_price as \"totalPrice\", oo.user_id as \"userId\", \n" +
        "INITCAP(u.full_name) as \"fullName\", u.email, u.profile_path as \"profileUrl\", oo.mark_as_read as \"markAsRead\" \n" +
        "FROM onsite_order oo  \n" +
        "JOIN users u ON u.id = oo.user_id \n" +
        "where u.id = ?1 and  oo.approval_status = 'DELIVERED' and  \n" +
        "case when ?2 = 'UNPAID' then oo.pay_status = 'UNPAID' \n" +
        "   when ?2 = 'PAID' then oo.pay_status = 'PAID' \n" +
        "   when ?2 = 'PARTIAL_PAID' then  oo.pay_status = 'PARTIAL_PAID' \n" +
        " else true\n" +
        "end) foo"
//            "where oo.pay_status = 'UNPAID' "
            , nativeQuery = true)
    Page<Map<String, Object>> getOnsiteOrderOfUserPaginated(Long id, String showStatus, Pageable pageable);

    @Query(value = "  SELECT oo.id, to_char(oo.created_date, 'YYYY-MM-DD HH:MI AM') as \"date\",  INITCAP(oo.pay_status) as \"payStatus\", oo.pay_status as \"payStatusCheck\", \n" +
            "oo.table_number as \"tableNumber\",  \n" +
            "case \n" +
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
            "where oo.is_active  and oo.user_id = ?3 and \n" +
            "case when ?5 is true then true else oo.approval_status = 'DELIVERED' end and \n " +
            "case when ?4 is null  then cast(oo.created_date as date) between ?1 and ?2 \n" +
            "when ?4 = 'PAID' then (cast(oo.created_date as date) between ?1 and ?2) and oo.pay_status = ?4 \n" +
            "else oo.pay_status in ('UNPAID', 'PARTIAL_PAID') end \n" +
            "order by oo.created_date desc",
            countQuery = "select count(*) from (\n" +
                    "  SELECT oo.id,  INITCAP(oo.pay_status) as \"payStatus\", oo.pay_status as \"payStatusCheck\", \n" +
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
                    "where oo.is_active and oo.user_id = ?3 and \n" +
                    "case when ?5 is true then true else oo.approval_status = 'DELIVERED' end and \n" +
                    "case when ?4 is null  then cast(oo.created_date as date) between ?1 and ?2 \n" +
                    "when ?4 = 'PAID' then (cast(oo.created_date as date) between ?1 and ?2) and oo.pay_status = ?4 \n" +
                    "else oo.pay_status in ('UNPAID', 'PARTIAL_PAID') end \n" +
                    "order by oo.created_date desc) foo",
            nativeQuery = true)
    Page<Map<String, Object>> getUserOrderPaginated(LocalDate fromDate, LocalDate toDate, Long userId, String payStatus, boolean isToday, Pageable pageable);


    @Query(nativeQuery = true,
            value = "select * from onsite_order oo where oo.is_active and oo.pay_status = 'UNPAID' and oo.approval_status = 'DELIVERED' and oo.user_id = ?1")
    List<OnsiteOrder> findUnpaidOnsiteOrdersOfUserByUserId(Long userId);
}
