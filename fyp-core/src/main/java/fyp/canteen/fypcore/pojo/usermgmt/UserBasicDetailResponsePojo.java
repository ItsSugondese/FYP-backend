package fyp.canteen.fypcore.pojo.usermgmt;

import fyp.canteen.fypcore.enums.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserBasicDetailResponsePojo {
    private String fullName;

    private UserType userType;


    private Long id;

    private String userEmail;

}
