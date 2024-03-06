package fyp.canteen.fypcore.model.entity.ordermgmt;

import fyp.canteen.fypcore.enums.ApprovalStatus;
import fyp.canteen.fypcore.enums.DeliveryStatus;
import fyp.canteen.fypcore.enums.OrderType;
import fyp.canteen.fypcore.enums.PayStatus;
import fyp.canteen.fypcore.generics.api.AuditActiveAbstract;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "onsite_order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnsiteOrder extends AuditActiveAbstract {

    @Id
    @SequenceGenerator(name = "onsite_order_gen", sequenceName = "onsite_order_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "onsite_order_gen")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_onsite_order_user"), nullable = false)
    private User user;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PayStatus payStatus = PayStatus.UNPAID;

    private LocalDateTime orderedTime = LocalDateTime.now();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    private Integer tableNumber;

    @Column(columnDefinition = "double precision default 0.0")
    private double totalPrice;

    @Column(columnDefinition = "boolean default false")
    private boolean markAsRead = false;

    @Column(columnDefinition = "boolean default false")
    private boolean hasFeedback = false;



}
