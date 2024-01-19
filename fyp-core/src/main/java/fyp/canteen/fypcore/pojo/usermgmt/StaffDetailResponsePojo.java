package fyp.canteen.fypcore.pojo.usermgmt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffDetailResponsePojo {
    private Long id;
    private String contactNumber;
    private String email;
    private String startedWorkingOn;
    private String fullName;
    private boolean isAccountNonLocked;
}
