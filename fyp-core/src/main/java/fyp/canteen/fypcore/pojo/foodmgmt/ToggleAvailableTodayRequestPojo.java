package fyp.canteen.fypcore.pojo.foodmgmt;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ToggleAvailableTodayRequestPojo {
    @NotNull
    private Long foodId;
    @NotNull
    private Boolean status;
}
