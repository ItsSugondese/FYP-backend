package fyp.canteen.fypcore.pojo.pagination;

import fyp.canteen.fypcore.enums.PayStatus;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnsiteOrderOfUserPaginationRequestPojo extends PaginationRequest {
    private Long userId;
    private PayStatus payStatus = null;
}
