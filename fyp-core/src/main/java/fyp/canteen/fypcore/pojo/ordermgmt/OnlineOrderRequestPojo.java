package fyp.canteen.fypcore.pojo.ordermgmt;

import fyp.canteen.fypcore.enums.ApprovalStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OnlineOrderRequestPojo {
    private Long id;

    @NotNull
    private List<FoodOrderRequestPojo> foodOrderList = new ArrayList<>();
    private List<Long> removeFoodId = new ArrayList<>();

    private LocalTime fromTime;

    private LocalTime toTime;
    private ApprovalStatus approvalStatus;
}
