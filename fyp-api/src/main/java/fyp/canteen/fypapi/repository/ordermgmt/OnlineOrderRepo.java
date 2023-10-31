package fyp.canteen.fypapi.repository.ordermgmt;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnlineOrder;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface OnlineOrderRepo extends GenericSoftDeleteRepository<OnlineOrder, Long> {

    @Query(value = "select count(*)  from online_order oo where CAST(oo.created_date AS DATE) = ?1",nativeQuery = true)
    int noOfOrders(LocalDate date);
}
