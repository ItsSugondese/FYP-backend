package fyp.canteen.fypcore.model.entity.auth.jwt;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtRequest {
    @NotNull
    private String userEmail;
    @NotNull
    private String userPassword;
}
