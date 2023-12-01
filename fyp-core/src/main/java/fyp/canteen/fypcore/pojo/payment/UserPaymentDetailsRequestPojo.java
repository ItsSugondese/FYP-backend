package fyp.canteen.fypcore.pojo.payment;

import fyp.canteen.fypcore.enums.PaymentMode;
import fyp.canteen.fypcore.model.entity.ordermgmt.OrderUserMapping;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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

    private Integer orderUserMappingId;
}
