package fyp.canteen.fypcore.model.notification;

import fyp.canteen.fypcore.generics.api.AuditActiveAbstract;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "announcement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Announcement extends AuditActiveAbstract {
    @Id
    @SequenceGenerator(name = "announcement_seq_gen", sequenceName = "announcement_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcement_seq_gen")
    private Long id;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String message;
}
