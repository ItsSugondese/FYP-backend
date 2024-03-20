package fyp.canteen.fypcore.pojo.announcement;

import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnnouncementPaginationRequest extends PaginationRequest {
    private String name   = "-1";
}
