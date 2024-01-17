package fyp.canteen.fypcore.model.entity.payment;

import fyp.canteen.fypcore.enums.PaymentMode;
import fyp.canteen.fypcore.generics.api.AuditActiveAbstract;
import fyp.canteen.fypcore.model.entity.ordermgmt.OnsiteOrder;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "user_payment_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPaymentDetails extends AuditActiveAbstract {

    @Id
    @SequenceGenerator(name = "user_payment_details_gen", sequenceName = "user_payment_details_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_payment_details_gen")
    private Long id;

    @Column(nullable = false, columnDefinition = "double precision default(0.00)")
    private Double totalAmount;

    @Column(columnDefinition = "double precision default(0.00)")
    private Double paidAmount = 0.00;

    @Column(columnDefinition = "double precision default(0.00)")
    private Double discount = 0.00;

    @Column(columnDefinition = "double precision default(0.00)")
    private Double dueAmount = 0.00;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate = LocalDate.now();

    private String remarks;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode = PaymentMode.CASH;

    @OneToOne
    @JoinColumn(name = "onsite_order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_user_payment_details_onsite_order"))
    private OnsiteOrder onsiteOrder;

}