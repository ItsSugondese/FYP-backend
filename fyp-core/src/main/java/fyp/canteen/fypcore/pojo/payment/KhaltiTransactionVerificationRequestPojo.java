package fyp.canteen.fypcore.pojo.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KhaltiTransactionVerificationRequestPojo {
    private String token;
    private Double amount;
}
