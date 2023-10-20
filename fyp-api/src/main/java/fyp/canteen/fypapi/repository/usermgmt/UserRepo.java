package fyp.canteen.fypapi.repository.usermgmt;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends GenericSoftDeleteRepository<User, Long> {

    @Query(value = "select case  when\n" +
            "    upper(email_address) = ?1 then 'Email Address Already Exists' end as \"Status\"\n" +
            "    from users order by \"Status\" limit 1", nativeQuery = true)
    String isUnique(String username);
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
