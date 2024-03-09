package fyp.canteen.fypcore.pojo.dashboard.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnsiteOrderDataPojo {
    private Integer total;
    private Integer delivered;
    private Integer pending;
    private Integer canceled;
}
