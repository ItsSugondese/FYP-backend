package fyp.canteen.fypcore.pojo.payment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentHistoryOfOrderResponsePojo {
    private String paidDate;
    private Double remainingAmount;
    private Double paidAmount;
    private Double dueAmount;
}
