package fyp.canteen.fypcore.pojo.dashboard.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDataPojo {
    private Integer totalUser;
    private Integer internal;
    private Integer external;
    private Integer latestUser;
    private Integer totalStaff;
    private Integer latestStaff;
}
