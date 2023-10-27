package fyp.canteen.fypapi.repository.foodmgmt;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenuPicture;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FoodPictureRepo extends GenericSoftDeleteRepository<FoodMenuPicture, Long> {

    @Query(value = "update food_menu_picture fp set fp.isActive= false where fp.id = ?1", nativeQuery = true)
    @Transactional
    @Modifying
    void deleteAllByFoodMenuId(Long foodMenuId);
}
