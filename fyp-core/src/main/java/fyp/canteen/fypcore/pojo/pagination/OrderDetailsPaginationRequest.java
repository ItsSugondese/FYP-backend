package fyp.canteen.fypcore.pojo.pagination;

import fyp.canteen.fypcore.enums.PayStatus;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderDetailsPaginationRequest extends PaginationRequest {
    private Boolean today = false;
    private PayStatus payStatus = null;
    public enum OrderTimeType{
        TODAY, SPECIFY
    }
}
