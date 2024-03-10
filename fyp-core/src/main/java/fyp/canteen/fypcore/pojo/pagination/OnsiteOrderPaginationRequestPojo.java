package fyp.canteen.fypcore.pojo.pagination;

import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class OnsiteOrderPaginationRequestPojo extends PaginationRequest {
    private Integer minuteRange = 30;
    private boolean read = false;
    private String name = "-1";
    private OnsiteOrderFilter onsiteOrderFilter;
    private Long userId = null;
    public enum OnsiteOrderFilter{
        PENDING, VIEWED, DELIVERED, CANCELED, PAID
    }
}
