package fyp.canteen.fypcore.model.notification;

import fyp.canteen.fypcore.generics.api.AuditActiveAbstract;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "notification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification extends AuditActiveAbstract {
    @Id
    @SequenceGenerator(name = "notification_seq_gen", sequenceName = "notification_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq_gen")
    private Integer id;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_notification_user"))
    private User user;

    @Column(columnDefinition = "boolean default false")
    private boolean isSeen;

}


