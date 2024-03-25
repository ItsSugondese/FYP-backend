package fyp.canteen.fypcore.pojo.pagination;

import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderDetailsPaginationRequest extends PaginationRequest {
    private boolean today = false;

    public enum OrderTimeType{
        TODAY, SPECIFY
    }
}
