package fyp.canteen.fypcore.pojo.notification;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequestPojo {
    private Long id;
    @NotNull
    private String message;
    @NotNull
    private Long userId;
    private Integer refId;
    private String refLink;
}

