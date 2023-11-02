package fyp.canteen.fypcore.model.entity.ordermgmt;

import fyp.canteen.fypcore.enums.ApprovalStatus;
import fyp.canteen.fypcore.generics.api.AuditActiveAbstract;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "online_order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnlineOrder extends AuditActiveAbstract {

    @Id
    @SequenceGenerator(name = "online_order_gen", sequenceName = "online_order_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "online_order_gen")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_online_order_user"), nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus approvalStatus;

    @Column(nullable = false)
    private String orderCode;

    @Column(nullable = false)
    private LocalTime fromTime;
    @Column(nullable = false)
    private LocalTime toTime;
}
