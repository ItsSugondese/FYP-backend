package fyp.canteen.fypcore.pojo.dashboard.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSummaryPojo {
    private Integer total;
    private Integer online;
    private Integer onsite;
}
