package fyp.canteen.fypapi.repository.foodmgmt;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface FoodMenuRepo extends GenericSoftDeleteRepository<FoodMenu, Long> {

    @Query(nativeQuery = true, value = "select fm.id as id, fm.\"name\" as name, fm.\"cost\" as cost, fm.description as description, fm.food_type as \"foodType\",\n" +
            "(select fmp.id  from food_menu_picture fmp where fmp.is_active and fmp.food_menu_id = fm.id) as \"photoId\",\n" +
            "fm.is_available_today as \"isAvailableToday\" \n" +
            "from food_menu fm where fm.is_active and  \n" +
            "case when ?1='-1' then true else fm.\"name\" ilike concat('%',?1,'%') end")
    Page<Map<String, Object>> getFoodMenuPageable(String name, Pageable pageable);
}
