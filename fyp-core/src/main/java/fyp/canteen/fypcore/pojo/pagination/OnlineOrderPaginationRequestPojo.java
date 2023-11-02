package fyp.canteen.fypcore.pojo.pagination;

import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class OnlineOrderPaginationRequestPojo extends PaginationRequest {
    private LocalTime fromTime = LocalTime.parse("00:00:00");
    private LocalTime toTime = LocalTime.parse("23:59:59");
}
