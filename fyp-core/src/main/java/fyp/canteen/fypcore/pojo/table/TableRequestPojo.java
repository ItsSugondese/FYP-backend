package fyp.canteen.fypcore.pojo.table;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TableRequestPojo {
    @NotNull
    private Integer tableNumber;
}
