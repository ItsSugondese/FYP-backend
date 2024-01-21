package fyp.canteen.fypcore.model.entity.usermgmt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fyp.canteen.fypcore.generics.api.AuditActiveAbstract;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_disable_history")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties
public class UserDisableHistory extends AuditActiveAbstract {

    @Id
    @SequenceGenerator(name = "users_disable_history_gen", sequenceName = "users_disable_history_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_disable_history_gen")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String remarks;
    private Boolean isDisabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_users_disable_history_user"))
    private User user;
}
