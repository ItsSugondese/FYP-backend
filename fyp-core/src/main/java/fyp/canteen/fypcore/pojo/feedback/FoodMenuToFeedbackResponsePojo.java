package fyp.canteen.fypcore.pojo.feedback;

import fyp.canteen.fypcore.enums.FoodType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FoodMenuToFeedbackResponsePojo {
    private Long foodId;
    private BigDecimal price;
    private Long pictureId;
    private String foodName;
    private FoodType foodType;
    private boolean isGiven;
}
