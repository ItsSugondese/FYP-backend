package fyp.canteen.fypcore.pojo.jwt;

import fyp.canteen.fypcore.model.entity.auth.Role;
import fyp.canteen.fypcore.model.entity.usermgmt.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {
    private String jwtToken;
    private List<String> roles;
    private String username;
}
