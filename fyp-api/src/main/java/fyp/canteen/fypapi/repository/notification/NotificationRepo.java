package fyp.canteen.fypapi.repository.notification;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.notification.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface NotificationRepo extends GenericSoftDeleteRepository<Notification, Long> {
    @Query(nativeQuery = true, value = "SELECT \n" +
            "    to_char(n.created_date, 'YYYY-MM-DD HH:MI AM') as \"date\", \n" +
            "    n.is_seen as \"isSeen\",\n" +
            "    CASE \n" +
            "         WHEN split_part(n.message, E'\\n', 1)   = '' THEN NULL \n" +
            "         ELSE split_part(n.message, E'\\n', 1) \n" +
            "       END as message,\n" +
            "           CASE \n" +
            "         WHEN split_part(n.message, E'\\n', 2)   = '' THEN NULL \n" +
            "         ELSE split_part(n.message, E'\\n', 2) \n" +
            "       END as remark \n" +
            "FROM \n" +
            "    notification n \n" +
            "    JOIN users u ON u.id  = n.user_id \n" +
            "WHERE \n" +
            "    u.id = ?1 AND n.is_active \n" +
            "ORDER BY \n" +
            "    is_seen ASC, \n" +
            "    n.created_date DESC",
            countQuery = "select count(*) from (SELECT \n" +
                    "    to_char(n.created_date, 'YYYY-MM-DD HH:MI:SS') as \"date\", \n" +
                    "    n.is_seen as \"isSeen\",\n" +
                    "    CASE \n" +
                    "        WHEN POSITION('\\n' IN n.message) > 0 THEN \n" +
                    "            SUBSTRING(n.message, 1, POSITION('\\n' IN n.message) - 1)\n" +
                    "        ELSE \n" +
                    "            n.message \n" +
                    "    END AS message \n" +
                    "FROM \n" +
                    "    notification n \n" +
                    "    JOIN users u ON u.id  = n.user_id \n" +
                    "WHERE \n" +
                    "    u.id = ?1 AND n.is_active \n" +
                    "ORDER BY \n" +
                    "    is_seen ASC, \n" +
                    "    n.created_date DESC) foo")
    Page<Map<String, Object>> getAllNotificationMessageOfMember(Long userId, Pageable pageable);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update notification set is_seen = true where exists(select * from notification n\n" +
            "join users u on u.id = n.user_id\n" +
            "where n.is_active and n.is_seen is false and u.id = ?1 )")
    void updateAllMessageAsSeen(Long userId);

    @Query(nativeQuery = true, value = "select coalesce((select count(*) from notification n join users u on u.id = n.user_id \n" +
            "where n.is_active and n.is_seen is false and u.id = ?1), 0)")
    int getNewNotificationCount(Long userId);

//    @Query(nativeQuery = true, value = "select coalesce((select count(*) from notification n join member m on m.user_id = n.user_id \n" +
//            "where n.is_active and n.is_seen is false and m.user_id = ?1), 0)")
//    int getNewNotificationCountByUserId(Integer userId);



}
