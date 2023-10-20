package fyp.canteen.fypcore.model.entity.auth.jwt;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtRequest {

    private String userEmail;
    private String userPassword;
}
