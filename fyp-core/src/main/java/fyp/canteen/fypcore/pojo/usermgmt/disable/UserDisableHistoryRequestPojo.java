package fyp.canteen.fypcore.pojo.usermgmt.disable;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDisableHistoryRequestPojo {
    private String remarks;
    private Long userId;

    @NotNull
    private Boolean isDisabled;
}
