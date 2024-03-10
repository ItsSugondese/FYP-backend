package fyp.canteen.fypcore.pojo.pagination;

import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class OnlineOrderPaginationRequestPojo extends PaginationRequest {
    private Integer minDifference = 30;
    private String name = "-1";
}
