package fyp.canteen.fypcore.pojo.inventory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryMenuResponsePojo {
    private Long id;
    private Integer stock;
    private String date;
    private Integer remainingQuantity;
}
