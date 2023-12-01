package fyp.canteen.fypcore.pojo.ordermgmt;

import fyp.canteen.fypcore.enums.ApprovalStatus;
import fyp.canteen.fypcore.enums.DeliveryStatus;
import fyp.canteen.fypcore.enums.PayStatus;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderUserMappingRequestPojo {
    private Long id;

    private List<FoodOrderRequestPojo> foodOrderList = new ArrayList<>();
    private List<Long> removeFoodId = new ArrayList<>();

    private Long onlineOrderId;
    private PayStatus payStatus;
    private Integer tableNumber;
}
