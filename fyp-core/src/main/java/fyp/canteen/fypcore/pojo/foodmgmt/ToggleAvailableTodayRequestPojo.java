package fyp.canteen.fypcore.pojo.foodmgmt;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToggleAvailableTodayRequestPojo {
    @NotNull
    private Long foodId;
    @NotNull
    private Boolean status;
}
