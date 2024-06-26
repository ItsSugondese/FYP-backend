package fyp.canteen.fypapi.repository.feedback;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.feedback.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Map;

public interface FeedbackRepo extends JpaRepository<Feedback, Long> {

    @Query(nativeQuery = true, value = "select f.id,  to_char(f.created_date, 'YYYY-MM-DD HH:MI AM') as date, f.feedbacks, INITCAP(f.feedback_status) as \"feedbackStatus\", \n" +
            "f.is_anon as \"isAnonymous\", INITCAP(u.full_name) as username, \n" +
            "case when user_id is not null then u.profile_path else null end as \"userProfileUrl\"\n" +
            "from feedback f  left join users u on u.id = f.user_id \n" +
            "where f.is_active and f.food_id = ?1 and cast(f.created_date as date) between ?2 and ?3  order by f.created_date desc",
    countQuery = "select count(*) from (\n" +
            "select f.id,  to_char(f.created_date, 'YYYY-MM-DD HH:MI AM') as date, f.feedbacks, f.feedback_status as \"feedbackStatus\", \n" +
            "case when user_id is not null then false else true end as isAnonymous, u.full_name as username, u.profile_path as \"userProfileUrl\"\n" +
            "from feedback f  left join users u on u.id = f.user_id \n" +
            "where f.is_active and f.food_id = ?1 and cast(f.created_date as date) between ?2 and ?3 order by f.created_date desc) foo")
    Page<Map<String, Object>> getAllFeedbackPaginated(Long foodId, LocalDate fromDate, LocalDate toDate, Pageable pageable);
}
