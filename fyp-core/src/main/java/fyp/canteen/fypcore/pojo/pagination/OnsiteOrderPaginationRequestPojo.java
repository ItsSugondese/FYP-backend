package fyp.canteen.fypcore.pojo.pagination;

import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class OnsiteOrderPaginationRequestPojo extends PaginationRequest {
    private LocalTime timeRange = LocalTime.now().minusMinutes(30);
    private boolean read = false;
    private String name = "-1";
    private OnsiteOrderFilter onsiteOrderFilter;

    public enum OnsiteOrderFilter{
        PENDING, VIEWED, DELIVERED, CANCELED, PAID
    }
}
