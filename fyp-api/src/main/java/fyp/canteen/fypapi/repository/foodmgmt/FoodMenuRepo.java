package fyp.canteen.fypapi.repository.foodmgmt;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;

public interface FoodMenuRepo extends GenericSoftDeleteRepository<FoodMenu, Long> {
}
