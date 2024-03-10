package fyp.canteen.fypcore.pojo.dashboard.data;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RevenueDataPojo {
    private BigDecimal revenue;
    private BigDecimal paidAmount;
    private BigDecimal leftToPay;
    private Integer deliveredOrder;
}
