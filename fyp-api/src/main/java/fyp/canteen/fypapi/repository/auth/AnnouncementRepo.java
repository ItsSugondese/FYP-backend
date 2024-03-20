package fyp.canteen.fypapi.repository.auth;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.notification.Announcement;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Map;

public interface AnnouncementRepo extends GenericSoftDeleteRepository<Announcement, Long> {


    @Query(nativeQuery = true, value = "select to_char(a.created_date, 'YYYY-MM-DD at  HH:MI AM') as date, \n" +
            "    initcap( u.full_name) as \"postedBy\", u.id as \"userId\", a.message\n" +
            "    from announcement a \n" +
            "    join users u on a.created_by = u.id where \n" +
            "case when (cast(?1 as date) is null or cast(?2 as date) is null) then true else a.created_date between ?1 and ?2 end and \n" +
            "case when ?3 = '-1' then true else u.full_name ilike concat('%', ?3, '%') end\n" +
            "    order by a.created_date desc",
    countQuery = "select count(*) from (\n" +
            "select to_char(a.created_date, 'YYYY-MM-DD at HH:MI AM') as date, \n" +
            "    initcap( u.full_name) as \"postedBy\", u.profile_path as \"profileUrl\", a.message\n" +
            "    from announcement a \n" +
            "    join users u on a.created_by = u.id where\n" +
            "case when (cast(?1 as date) is null or cast(?2 as date) is null) then true else a.created_date between ?1 and ?2 end and \n" +
            "case when ?3 = '-1' then true else u.full_name ilike concat('%', ?3, '%') end\n" +
            "    order by a.created_date desc) foo")
    Page<Map<String, Object>> getAllAnnouncementPaginated(LocalDate fromDate, LocalDate toDate, String name, Pageable pageable);
}
