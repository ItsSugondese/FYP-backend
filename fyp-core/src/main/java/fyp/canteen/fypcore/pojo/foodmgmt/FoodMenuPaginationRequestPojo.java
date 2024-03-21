package fyp.canteen.fypcore.pojo.foodmgmt;

import fyp.canteen.fypcore.enums.FoodType;
import fyp.canteen.fypcore.enums.pojo.FoodFilter;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class  FoodMenuPaginationRequestPojo extends PaginationRequest {
    private String name = "-1";
    private FoodType foodType = null;
    private Boolean filter = null;
}
