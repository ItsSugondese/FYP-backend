package fyp.canteen.fypapi.service.food;

import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import fyp.canteen.fypcore.pojo.foodmgmt.FoodPictureRequestPojo;
import jakarta.servlet.http.HttpServletResponse;

public interface FoodPictureService {

    void saveFoodPicture(FoodPictureRequestPojo requestPojo, FoodMenu foodMenu);

    void deleteFoodPictureById(Long id);

    void showFoodPictureById(HttpServletResponse response, Long id);
}
