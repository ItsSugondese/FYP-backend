package fyp.canteen.fypcore.pojo.inventory;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class InventoryMenuRequestPojo {
    private Long id;
    @NotNull
    private Integer stock;
    @NotNull
    private Long foodId;
    private Integer remainingQuantity;
}
