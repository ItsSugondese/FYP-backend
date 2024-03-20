package fyp.canteen.fypcore.pojo.pagination;

import fyp.canteen.fypcore.enums.PayStatus;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderHistoryPaginationRequest extends PaginationRequest {
    private String name = "-1";
    private PayStatus payStatus = null;
}
