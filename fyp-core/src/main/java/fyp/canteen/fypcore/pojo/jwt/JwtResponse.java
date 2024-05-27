package fyp.canteen.fypcore.pojo.jwt;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
    private String jwtToken;
    private String email;
    private List<String> roles;
    private String username;
    private Long userId;
}
