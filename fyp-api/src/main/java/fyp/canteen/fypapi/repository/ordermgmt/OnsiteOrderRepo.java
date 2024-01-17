package fyp.canteen.fypapi.repository.ordermgmt;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnsiteOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

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
            "  oo.approval_status as \"approvalStatus\", oo.user_id as \"userId\", u.full_name as \"fullName\", u.email \n" +
            "FROM onsite_order oo \n" +
            "JOIN users u ON u.id = oo.user_id", nativeQuery = true)
    Page<Map<String, Object>> getOnsiteOrderPaginated(LocalTime timeRange, Pageable pageable);
//
//    @Query(value = "SELECT oo.id, oo.approval_status, oo.user_id, u.full_name, u.email \n" +
//            "FROM onsite_order oo \n" +
//            "JOIN users u ON u.id = oo.user_id  \n" +
//            "WHERE oo.is_active and cast(oo.created_date as date) = current_date and\n" +
//            "   CAST(oo.created_date AS time) BETWEEN cast('00:00' as time) AND cast('23:59' as time)", nativeQuery = true)
//    Page<Map<String, Object>> getOnsiteOrderPaginated(LocalTime timeRange, Pageable pageable);
}
