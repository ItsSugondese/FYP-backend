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
            "    upper(email_address) = ?1 then 'Email Address Already Exists' end as \"Status\"\n" +
            "    from users order by \"Status\" limit 1", nativeQuery = true)
    String isUnique(String username);
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);


    @Query(nativeQuery = true, value = "select u.id, u.full_name as \"fullName\", u.account_non_locked as \"accountNonLocked\", u.email, \n" +
            "u.profile_path as \"profilePath\" from users u where u.is_active")
    Page<Map<String, Object>> findAllUsers(Pageable pageable);
}
