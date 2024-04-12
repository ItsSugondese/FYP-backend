package fyp.canteen.fypcore.pojo.ordermgmt;

import fyp.canteen.fypcore.enums.ApprovalStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnlineOrderResponsePojo {
    private Long id;
    private String profileUrl;
    private String orderCode;
    private Double totalPrice;
    private String arrivalTime;
    private String arrivalTime24;
    private Long userId;
    private String fullName;
    private String email;
    private List<OrderFoodResponsePojo> orderFoodDetails;
    private ApprovalStatus approvalStatus;
}

