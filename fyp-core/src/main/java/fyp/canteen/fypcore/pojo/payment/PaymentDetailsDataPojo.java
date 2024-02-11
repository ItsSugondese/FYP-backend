package fyp.canteen.fypcore.pojo.payment;

import fyp.canteen.fypcore.enums.PaymentMode;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnsiteOrder;
import fyp.canteen.fypcore.model.entity.payment.UserPaymentDetails;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PaymentDetailsDataPojo {
    private Long id;
    private Double totalAmount;
    private Double paidAmount = 0.00;
    private Double discount = 0.00;
    private Double dueAmount = 0.00;
    private PaymentMode paymentMode = PaymentMode.CASH;
    private OnsiteOrder order;
}
