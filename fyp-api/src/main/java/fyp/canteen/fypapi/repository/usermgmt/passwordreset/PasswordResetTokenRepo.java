package fyp.canteen.fypapi.repository.usermgmt.passwordreset;

import fyp.canteen.fypcore.model.auth.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {

    @Query(value = "select *,to_char(expiry_date,'yyyy-MM-dd hh:MI:ss') as expiry_date_char from password_reset_token prt" +
            " where prt.token=?1 and prt.is_active=true and prt.status=1 and prt.expiry_date>=current_timestamp",
            nativeQuery = true)
    Optional<PasswordResetToken> findByToken(String resetToken);


    @Modifying
    @Query(value = "update password_reset_token set is_active=false ,status=0 where token=?", nativeQuery = true)
    Integer updateTokenIsActive(String resetToken);
}
