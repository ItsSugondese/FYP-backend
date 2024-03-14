package fyp.canteen.fypcore.pojo.usermgmt;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserFinanceDataPojo {
    private Integer sNo;
    private BigDecimal totalTransaction;
    private BigDecimal totalPaid;
    private BigDecimal dueAmount;
    private Long userId;
    private String fullName;
}
