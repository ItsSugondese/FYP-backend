package fyp.canteen.fypcore.pojo.table;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableRequestPojo {
    Long id;
    @NotNull
    private Integer tableNumber;
}
