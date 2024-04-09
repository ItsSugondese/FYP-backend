package fyp.canteen.fypcore.pojo.inventory;

import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryFoodPaginationRequest extends PaginationRequest {
    private Long foodId;
}
