package fyp.canteen.fypcore.pojo.ordermgmt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderFoodResponsePojo {
    private Long id;
    private Integer quantity;
    private String foodName;
    private Double cost;
    private Double totalPrice;
}
