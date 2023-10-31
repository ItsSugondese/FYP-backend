package fyp.canteen.fypcore.pojo.ordermgmt;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderFoodMappingRequestPojo {
    List<FoodOrderRequestPojo> foodOrderList;
    List<Long> removeFoodId = new ArrayList<>();
}
