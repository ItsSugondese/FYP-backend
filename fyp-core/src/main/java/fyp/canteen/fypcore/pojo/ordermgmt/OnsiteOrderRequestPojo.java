package fyp.canteen.fypcore.pojo.ordermgmt;

import fyp.canteen.fypcore.enums.PayStatus;
import lombok.*;

import java.time.LocalDateTime;
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
    @Builder.Default
    private List<Long> removeFoodId = new ArrayList<>();
    private PayStatus payStatus;
    private Integer tableNumber;
    private double totalPrice;
    private LocalDateTime orderedTime;
    private Long userId;
    @Builder.Default
    private boolean fromOnline = false;
}
