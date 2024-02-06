package fyp.canteen.fypcore.pojo.feedback;

import fyp.canteen.fypcore.enums.FeedbackStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackResponsePojo {
    private Long id;
    private String feedback;
    private FeedbackStatus feedbackStatus;
    private boolean isAnonymous;
    private String username;
    private String userProfileUrl;
    private String date;
}
