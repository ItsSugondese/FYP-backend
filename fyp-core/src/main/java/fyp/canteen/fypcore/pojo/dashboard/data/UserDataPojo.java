package fyp.canteen.fypcore.pojo.dashboard.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDataPojo {
    private Integer total;
    private Integer internal;
    private Integer external;
    private Integer latest;
}
