package fyp.canteen.fypcore.model.entity.ordermgmt;

import fyp.canteen.fypcore.enums.ApprovalStatus;
import fyp.canteen.fypcore.enums.DeliveryStatus;
import fyp.canteen.fypcore.enums.OrderType;
import fyp.canteen.fypcore.enums.PayStatus;
import fyp.canteen.fypcore.generics.api.AuditActiveAbstract;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_user_mapping")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderUserMapping extends AuditActiveAbstract {

    @Id
    @SequenceGenerator(name = "order_user_mapping_gen", sequenceName = "order_user_mapping_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_user_mapping_gen")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_order_user_mapping_user"), nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "online_order_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_order_user_mapping_online_order"))
    private OnlineOrder onlineOrder;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    private Integer tableNumber;


}
