package fyp.canteen.fypcore.pojo.foodmgmt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fyp.canteen.fypcore.enums.FoodType;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties
public class FoodMenuRequestPojo {

    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Double cost;

    @NotNull
    private FoodType foodType;

    private Long photoId;
}
