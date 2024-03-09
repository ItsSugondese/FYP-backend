package fyp.canteen.fypcore.pojo.dashboard.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDataPojo {
    private Integer totalOrder;
    private Integer totalPending;
    private OnsiteOrderDataPojo onsiteOrder;
    private OnlineOrderDataPojo onlineOrder;
}
