package fyp.canteen.fypcore.pojo.dashboard;

import fyp.canteen.fypcore.enums.FoodType;
import fyp.canteen.fypcore.enums.RevenueFilterType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SalesDataRequestPojo {
    private Integer limit = null;
    private RevenueFilterType filterType = RevenueFilterType.SALES;
    private LocalDate fromDate = LocalDate.now();
    private LocalDate toDate = LocalDate.now();
    private FoodType foodType = null;
}
