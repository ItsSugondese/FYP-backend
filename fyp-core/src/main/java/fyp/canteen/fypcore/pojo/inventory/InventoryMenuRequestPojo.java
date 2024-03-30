package fyp.canteen.fypcore.pojo.inventory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryMenuRequestPojo {
    private Long id;
    private Integer stock;
    private Long foodId;
    private Integer remainingQuantity;
}
