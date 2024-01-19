package fyp.canteen.fypcore.pojo.usermgmt;

import fyp.canteen.fypcore.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StaffDetailsRequestPojo {
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private String fullName;
    @NotNull
    private Long profileId;
    @NotNull
    private String contactNumber;
}
