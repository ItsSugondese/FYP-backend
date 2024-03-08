package fyp.canteen.fypcore.pojo.dashboard.data;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RevenueDataPojo {
    private BigDecimal totalSales;
    private BigDecimal paidAmount;
    private BigDecimal amountLeftToPay;
}
