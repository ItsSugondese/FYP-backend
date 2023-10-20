package fyp.canteen.fypcore.pojo.usermgmt;

import fyp.canteen.fypcore.enums.UserType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsRequestPojo {

    private Long id;
    private String password;
    private String email;
    private String fullName;
    private UserType userType;
    private Boolean isEmailAddressVerified;
    private Long profileId;
    private String profilePath;
}
