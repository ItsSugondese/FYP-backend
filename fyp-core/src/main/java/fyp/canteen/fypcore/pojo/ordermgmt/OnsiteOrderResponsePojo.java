package fyp.canteen.fypcore.pojo.ordermgmt;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OnsiteOrderResponsePojo {
    private String profileUrl;
    private String approvalStatus;
    private String orderedTime;
    private Double totalPrice;
    private String fullName;
    private Integer userId;
    private List<OrderFoodResponsePojo> orderFoodDetails;
    private Boolean markAsRead;
    private String payStatusCheck;
    private Double remainingAmount;
    private Long id;
    private String payStatus;
    private String email;
    private String orderStatus;
    private Boolean feedbackStatus;
}
