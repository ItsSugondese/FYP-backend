package fyp.canteen.fypcore.pojo.ordermgmt;

import fyp.canteen.fypcore.pojo.foodmgmt.FoodMenuRequestPojo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderFoodResponsePojo {
    private Long id;
    private Integer quantity;
    private String foodName;
    private Double cost;
    private Double totalPrice;
    private Long photoId;
    private Long foodId;
    private FoodMenuRequestPojo foodMenu;
}
