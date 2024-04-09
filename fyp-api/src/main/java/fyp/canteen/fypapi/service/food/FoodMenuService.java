package fyp.canteen.fypapi.service.food;

import fyp.canteen.fypcore.enums.pojo.FoodFilter;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import fyp.canteen.fypcore.pojo.foodmgmt.FoodMenuPaginationRequestPojo;
import fyp.canteen.fypcore.pojo.foodmgmt.FoodMenuRequestPojo;
import fyp.canteen.fypcore.pojo.foodmgmt.FoodMenuResponsePojo;
import fyp.canteen.fypcore.pojo.foodmgmt.ToggleAvailableTodayRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface FoodMenuService {
    void saveFoodMenu(FoodMenuRequestPojo requestPojo);
    PaginationResponse getFoodMenuPageable(FoodMenuPaginationRequestPojo requestPojo);
    void toggleAvailability(ToggleAvailableTodayRequestPojo requestPojo);

    List<FoodMenuRequestPojo> getAllFoodMenu(FoodFilter foodFilter);

    void getFoodPhoto(HttpServletResponse response, Long id);

    FoodMenuResponsePojo getFoodMenuById(Long id);

    FoodMenu findById(Long id);
}
