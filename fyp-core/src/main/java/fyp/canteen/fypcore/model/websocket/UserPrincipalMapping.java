package fyp.canteen.fypcore.model.websocket;

import com.sun.security.auth.UserPrincipal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "user_principal_mapping")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserPrincipalMapping  {

    @Id
    private Long userId;
    @Column(nullable = false)
    private UserPrincipal principal;
}
