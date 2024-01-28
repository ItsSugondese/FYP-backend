package fyp.canteen.fypcore.pojo.feedback;

import fyp.canteen.fypcore.enums.FeedbackStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FeedbackRequestPojo {
    private Long id;

    @NotNull
    private FeedbackStatus feedbackStatus;

    @NotNull
    private Long foodId;

    @NotNull
    private String feedbacks;

    @NotNull
    private Boolean isAnonymous;
}
