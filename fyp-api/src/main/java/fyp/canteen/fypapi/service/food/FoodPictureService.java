package fyp.canteen.fypapi.service.food;

import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import fyp.canteen.fypcore.pojo.foodmgmt.FoodPictureRequestPojo;

public interface FoodPictureService {

    void saveFoodPicture(FoodPictureRequestPojo requestPojo, FoodMenu foodMenu);

    void deleteFoodPictureById(Long id);
}
