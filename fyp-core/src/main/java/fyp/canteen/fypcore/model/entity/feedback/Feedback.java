package fyp.canteen.fypcore.model.entity.feedback;

import fyp.canteen.fypcore.enums.FeedbackStatus;
import fyp.canteen.fypcore.generics.api.AuditActiveAbstract;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "feedback")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Feedback extends AuditActiveAbstract {

    @Id
    @SequenceGenerator(name = "feedback_gen", sequenceName = "feedback_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_gen")
    private Long id;

    @Enumerated(EnumType.STRING)
    private FeedbackStatus feedbackStatus;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String feedbacks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_feedback_food_menu"), nullable = false)
    private FoodMenu foodMenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_feedbacks_user"))
    private User user;

}
