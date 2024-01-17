package fyp.canteen.fypcore.pojo.pagination;

import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class OnsiteOrderPaginationRequestPojo extends PaginationRequest {
    LocalTime timeRange = LocalTime.now().minusMinutes(30);
}
