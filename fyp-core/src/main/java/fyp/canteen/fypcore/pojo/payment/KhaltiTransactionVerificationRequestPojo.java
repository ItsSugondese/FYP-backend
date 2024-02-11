package fyp.canteen.fypcore.pojo.payment;

import fyp.canteen.fypcore.pojo.ordermgmt.OnsiteOrderRequestPojo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KhaltiTransactionVerificationRequestPojo {
    private String token;
    private Double amount;
    private OnsiteOrderRequestPojo onsiteOrder;
}
