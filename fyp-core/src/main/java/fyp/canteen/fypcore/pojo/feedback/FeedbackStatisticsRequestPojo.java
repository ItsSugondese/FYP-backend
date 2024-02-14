package fyp.canteen.fypcore.pojo.feedback;

import fyp.canteen.fypcore.utils.data.DateRangeHolder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackStatisticsRequestPojo extends DateRangeHolder {
    private Long foodId;
}
