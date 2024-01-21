package fyp.canteen.fypapi.repository.usermgmt;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.usermgmt.UserDisableHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface UserDisableHistoryRepo extends GenericSoftDeleteRepository<UserDisableHistory, Long> {

    @Query(nativeQuery = true, value = "select udh.id, to_char(udh.created_date, 'YYYY-MM-DD HH:MI AM') as date, \n" +
            "udh.is_disabled as \"isDisabled\", udh.remarks as remarks\n" +
            "from users_disable_history udh where udh.is_active and udh.user_id = ?1 ")
    Page<Map<String, Object>> getDisableHistoryPaginated(Long userId, Pageable pageable);
}
