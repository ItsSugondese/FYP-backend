package fyp.canteen.fypcore.utils.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DateRangeHolder {
    private  LocalDate fromDate;
    private  LocalDate toDate;
    private Integer week;
}

