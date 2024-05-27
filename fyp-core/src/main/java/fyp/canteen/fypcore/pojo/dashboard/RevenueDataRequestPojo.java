package fyp.canteen.fypcore.pojo.dashboard;

import fyp.canteen.fypcore.utils.data.DateRangeHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RevenueDataRequestPojo extends DateRangeHolder {
    private LocalDate fromDate ;
    private LocalDate toDate;
}
