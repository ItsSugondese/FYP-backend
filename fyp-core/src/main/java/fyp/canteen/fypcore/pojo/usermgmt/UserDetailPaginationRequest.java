package fyp.canteen.fypcore.pojo.usermgmt;

import fyp.canteen.fypcore.enums.UserType;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailPaginationRequest extends PaginationRequest {
    private UserType userType;
    private String name = "-1";
}
