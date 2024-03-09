package fyp.canteen.fypcore.pojo.dashboard;

import fyp.canteen.fypcore.utils.data.DateRangeHolder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FoodMenuDataRequestPojo extends DateRangeHolder {
    private LocalDate fromDate = LocalDate.now();
    private LocalDate toDate = LocalDate.now();
}
