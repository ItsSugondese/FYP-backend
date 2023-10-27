package fyp.canteen.fypcore.pojo.foodmgmt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class FoodMenuRequestPojo {

    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Double cost;

    @NotNull
    private Boolean isPackage;

    private List<String> menuItems = new ArrayList<>();

    private Long photoId;
    private Long removeFileId;
}
