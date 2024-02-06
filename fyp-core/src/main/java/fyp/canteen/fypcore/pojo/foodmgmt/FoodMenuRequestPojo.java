package fyp.canteen.fypcore.pojo.foodmgmt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fyp.canteen.fypcore.enums.FoodType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@JsonIgnoreProperties
public class FoodMenuRequestPojo {

    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Double cost;

    private FoodType foodType;
    @NotNull
    private Boolean isPackage;

    private List<String> menuItems = new ArrayList<>();

    private Boolean isAvailableToday;

    private Long photoId;
    @JsonIgnore
    private String menuItemsString;

    public void setMenuItemsString(String menuItemsString) {
        this.menuItemsString = menuItemsString;
        this.menuItems = Arrays.asList(menuItemsString.split(","));
    }

}
