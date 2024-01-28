package fyp.canteen.fypapi.repository.usermgmt.passwordreset;

import fyp.canteen.fypcore.model.auth.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {

}
