package fyp.canteen.fypcore.pojo.usermgmt.disable;

import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDisableHistoryPaginationRequest extends PaginationRequest {
    private Long userId;
}
