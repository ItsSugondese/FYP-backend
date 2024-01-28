package fyp.canteen.fypcore.pojo.resetpassword;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fyp.canteen.fypcore.enums.PasswordSetType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordDetailRequestPojo {
    private String userEmail;
    private String password;
    private String resetToken;
    private String baseUrl;
    private String fullName;
    @JsonIgnore
    private Long tokenId;
    private PasswordSetType passwordSetType = PasswordSetType.RESET;
}
