package fyp.canteen.fypcore.pojo.feedback;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodMenuToFeedbackResponsePojo {
    private Long foodId;
    private Long pictureId;
    private String foodName;
    private boolean isGiven;
}
