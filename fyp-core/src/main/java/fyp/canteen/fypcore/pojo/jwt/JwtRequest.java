package fyp.canteen.fypcore.pojo.jwt;

import fyp.canteen.fypcore.enums.Device;
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
    private Device device = Device.WEBSITE;
}
