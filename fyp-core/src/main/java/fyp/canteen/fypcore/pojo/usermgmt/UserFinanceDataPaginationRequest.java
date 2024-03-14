package fyp.canteen.fypcore.pojo.usermgmt;

import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFinanceDataPaginationRequest extends PaginationRequest {
    private String name;
}
