package fyp.canteen.fypcore.pojo.feedback;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackStatisticsResponsePojo {
    private Double positivePercentage;
    private Double negativePercentage;
    private Double neutralPercentage;
    private Double totalFeedback;
    private Double positiveCount;
    private Double negativeCount;
    private Double neutralCount;
    private String sentiment;
}
