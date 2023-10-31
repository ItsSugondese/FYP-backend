package fyp.canteen.fypcore.pojo.ordermgmt;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodOrderRequestPojo {
    private Long id;
    @NotNull
    private Long foodId;
    @NotNull
    private Integer quantity;
}
