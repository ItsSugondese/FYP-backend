package fyp.canteen.fypapi.repository.auth;

import fyp.canteen.fypcore.model.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, String> {
}
