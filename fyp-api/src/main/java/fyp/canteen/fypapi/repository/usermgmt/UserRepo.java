package fyp.canteen.fypapi.repository.usermgmt;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;
import java.util.Optional;

public interface UserRepo extends GenericSoftDeleteRepository<User, Long> {

    @Query(value = "select case  when\n" +
            "    upper(email) = ?1 then 'Email Address Already Exists' end as \"Status\"\n" +
            "    from users order by \"Status\" limit 1", nativeQuery = true)
    String isUnique(String username);
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);


    @Query(nativeQuery = true,
            value = "select u.id, INITCAP(u.full_name) as \"fullName\", u.account_non_locked as \"accountNonLocked\", u.email, \n" +
            "u.profile_path as \"profilePath\", u.contact_number as \"contactNumber\" from users u where u.is_active and u.user_type = ?1 and \n" +
            "case when ?2 = '-1' then true else u.full_name ilike concat('%',?2,'%') end order by u.last_modified_date desc",
     countQuery= "select count(*) from (select u.id, INITCAP(u.full_name) as \"fullName\", u.account_non_locked as \"accountNonLocked\", u.email, \n" +
            "u.profile_path as \"profilePath\", u.contact_number as \"contactNumber\" from users u where u.is_active and u.user_type = ?1 and \n" +
            "case when ?2 = '-1' then true else u.full_name ilike concat('%',?2,'%') end order by u.last_modified_date desc) foo")
    Page<Map<String, Object>> findAllUsers(String userType, String name, Pageable pageable);

    @Query(nativeQuery = true, value = "select u.id, INITCAP(u.full_name) as \"fullName\", u.account_non_locked as \"accountNonLocked\", u.email, \n" +
            "u.profile_path as \"profilePath\", u.contact_number as \"contactNumber\" from users u where u.is_active and u.user_type = 'STAFF'")
    Page<Map<String, Object>> findAllStaff(Pageable pageable);
}
