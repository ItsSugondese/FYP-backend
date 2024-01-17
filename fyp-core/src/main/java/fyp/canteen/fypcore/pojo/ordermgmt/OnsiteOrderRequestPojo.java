package fyp.canteen.fypcore.pojo.ordermgmt;

import fyp.canteen.fypcore.enums.PayStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnsiteOrderRequestPojo {
    private Long id;

    private List<FoodOrderRequestPojo> foodOrderList = new ArrayList<>();
    private List<Long> removeFoodId = new ArrayList<>();

    private Long onlineOrderId;
    private PayStatus payStatus;
    private Integer tableNumber;
}
