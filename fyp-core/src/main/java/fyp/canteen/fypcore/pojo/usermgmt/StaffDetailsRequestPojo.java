package fyp.canteen.fypcore.pojo.usermgmt;

import fyp.canteen.fypcore.enums.UserType;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffDetailsRequestPojo {
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private String fullName;
//    @NotNull
    private Long profileId;
    @NotNull
    private String contactNumber;
    private String baseUrl;
    private UserType userType;
}
