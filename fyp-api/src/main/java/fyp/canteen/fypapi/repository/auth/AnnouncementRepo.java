package fyp.canteen.fypapi.repository.auth;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.notification.Announcement;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface AnnouncementRepo extends GenericSoftDeleteRepository<Announcement, Long> {


    @Query(nativeQuery = true, value = "select to_char(a.created_date, 'YYYY-MM-DD HH:MI AM') as date, \n" +
            "    initcap( u.full_name) as \"postedBy\", u.profile_path as \"profileUrl\", a.message\n" +
            "    from announcement a \n" +
            "    join users u on a.created_by = u.id\n" +
            "    order by a.created_date desc",
    countQuery = "select count(*) from (\n" +
            "select to_char(a.created_date, 'YYYY-MM-DD HH:MI AM') as date, \n" +
            "    initcap( u.full_name) as \"postedBy\", u.profile_path as \"profileUrl\", a.message\n" +
            "    from announcement a \n" +
            "    join users u on a.created_by = u.id\n" +
            "    order by a.created_date desc) foo")
    Page<Map<String, Object>> getAllAnnouncementPaginated(Pageable pageable);
}
