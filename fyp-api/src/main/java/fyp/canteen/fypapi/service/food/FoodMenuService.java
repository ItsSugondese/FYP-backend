package fyp.canteen.fypapi.service.food;

import fyp.canteen.fypcore.enums.pojo.FoodFilter;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import fyp.canteen.fypcore.pojo.foodmgmt.FoodMenuRequestPojo;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface FoodMenuService {
    void saveFoodMenu(FoodMenuRequestPojo requestPojo);

    List<FoodMenuRequestPojo> getAllFoodMenu(FoodFilter foodFilter);

    void getFoodPhoto(HttpServletResponse response, Long id);

    FoodMenu findById(Long id);
}
