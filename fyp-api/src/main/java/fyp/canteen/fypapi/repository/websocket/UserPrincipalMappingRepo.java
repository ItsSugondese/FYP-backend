package fyp.canteen.fypapi.repository.websocket;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.websocket.UserPrincipalMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPrincipalMappingRepo extends JpaRepository<UserPrincipalMapping, Long> {
}
