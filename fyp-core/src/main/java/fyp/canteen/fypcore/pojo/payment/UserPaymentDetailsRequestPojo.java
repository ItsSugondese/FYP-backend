package fyp.canteen.fypcore.pojo.payment;

import fyp.canteen.fypcore.enums.PaymentMode;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPaymentDetailsRequestPojo {
    private Long id;
    @NotNull
    private Double totalAmount;
    @NotNull
    @Builder.Default
    private Double paidAmount = 0.00;
    @Builder.Default
    private Double discount = 0.00;
    @Builder.Default
    private Double dueAmount = 0.00;

    private String remarks;

    @Builder.Default
    private PaymentMode paymentMode = PaymentMode.CASH;

    @NotNull
    private Long onsiteOrderId;
    @NotNull
    private Long userId;
}
