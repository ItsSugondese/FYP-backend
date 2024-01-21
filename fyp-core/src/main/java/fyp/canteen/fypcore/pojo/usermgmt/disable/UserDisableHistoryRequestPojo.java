package fyp.canteen.fypcore.pojo.usermgmt.disable;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDisableHistoryRequestPojo {
    private String remarks;
    private Long userId;

    @NotNull
    private Boolean isDisabled;
}
