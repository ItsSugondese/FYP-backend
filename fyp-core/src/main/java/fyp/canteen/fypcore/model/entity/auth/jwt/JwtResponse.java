package fyp.canteen.fypcore.model.entity.auth.jwt;

import fyp.canteen.fypcore.model.entity.usermgmt.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {
    private String jwtToken;
}
