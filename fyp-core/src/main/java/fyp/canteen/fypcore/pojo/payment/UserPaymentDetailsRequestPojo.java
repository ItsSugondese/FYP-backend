package fyp.canteen.fypcore.pojo.payment;

import fyp.canteen.fypcore.enums.PaymentMode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserPaymentDetailsRequestPojo {
    private Long id;
    @NotNull
    private Double totalAmount;
    @NotNull
    private Double paidAmount = 0.00;
    private Double discount = 0.00;
    private Double dueAmount = 0.00;

    private String remarks;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode = PaymentMode.CASH;

    private Integer onsiteOrderId;
}
