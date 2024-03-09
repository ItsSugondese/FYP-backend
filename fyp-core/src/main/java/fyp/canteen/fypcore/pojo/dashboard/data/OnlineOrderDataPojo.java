package fyp.canteen.fypcore.pojo.dashboard.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnlineOrderDataPojo {
    private Integer total;
    private Integer approved;
    private Integer pending;
}
