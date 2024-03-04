package fyp.canteen.fypapi.repository.ordermgmt;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnlineOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public interface OnlineOrderRepo extends GenericSoftDeleteRepository<OnlineOrder, Long> {

    @Query(value = "select count(*)  from online_order oo where CAST(oo.created_date AS DATE) = ?1",nativeQuery = true)
    int noOfOrders(LocalDate date);

//    @Query(value = "select oo.id, oo.approval_status, oo.order_code ,\n" +
//            "to_char(oo.arrival_time, 'HH:mi am' ) as arrival_time, \n" +
//            "    (string_to_array(oo.order_code , ' ')) [2] as order_code, oo.user_id, u.full_name, u.email \n" +
//            "   from online_order oo join users u on u.id = oo.user_id  where oo.is_active and (oo.arrival_time between ?1 \n" +
//            "and ?2)  and cast(oo.created_date as date) = current_date order by oo.arrival_time asc",
//            countQuery = "select count(*) from (select oo.id, oo.approval_status, oo.order_code ,\n" +
//                    "to_char(oo.arrival_time, 'HH:mi am' ) as arrival_time, \n" +
//                    "    (string_to_array(oo.order_code , ' ')) [2] as order_code, oo.user_id, u.full_name, u.email \n" +
//                    "   from online_order oo join users u on u.id = oo.user_id  where oo.is_active and (oo.arrival_time between ?1 \n" +
//                    "and ?2)  and cast(oo.created_date as date) = current_date order by oo.arrival_time asc) a",
//            nativeQuery = true)
//    Page<Map<String, Object>> getOnlineOrderPaginated(LocalTime fromTime, LocalTime toTime, Pageable pageable);

    @Query(value = "select oo.id, u.profile_path as \"profileUrl\",  oo.order_code , oo.total_price as \"totalPrice\",\n" +
            "to_char(oo.arrival_time, 'HH:mi am' ) as \"arrivalTime\", \n" +
            "    (string_to_array(oo.order_code , ' ')) [2] as \"orderCode\", oo.user_id as \"userId\", INITCAP(u.full_name) as \"fullName\", u.email \n" +
            "   from online_order oo join users u on u.id = oo.user_id  where oo.is_active",
            countQuery = "select count(*) from (select oo.id, oo.approval_status, oo.order_code ,\n" +
                    "to_char(oo.arrival_time, 'HH:mi am' ) as arrival_time, \n" +
                    "    (string_to_array(oo.order_code , ' ')) [2] as order_code, oo.user_id, u.full_name, u.email \n" +
                    "   from online_order oo join users u on u.id = oo.user_id  where oo.is_active) a",
            nativeQuery = true)
    Page<Map<String, Object>> getOnlineOrderPaginated(LocalTime fromTime, LocalTime toTime, Pageable pageable);
}
