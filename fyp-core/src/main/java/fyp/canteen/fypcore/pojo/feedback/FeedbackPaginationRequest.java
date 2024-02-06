package fyp.canteen.fypcore.pojo.feedback;

import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackPaginationRequest extends PaginationRequest {
    private Long foodId;
}
