package fyp.canteen.fypcore.pojo.usermgmt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailResponsePojo {
    private Long id;
    private String fullName;
    private boolean isAccountNonLocked;
    private String email;
    private String profilePath;
}
