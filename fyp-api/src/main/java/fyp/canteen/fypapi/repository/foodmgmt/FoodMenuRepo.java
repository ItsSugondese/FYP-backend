package fyp.canteen.fypapi.repository.foodmgmt;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface FoodMenuRepo extends GenericSoftDeleteRepository<FoodMenu, Long> {

    @Query(nativeQuery = true, value = "select fm.id as id, fm.is_auto as \"isAuto\", fm.\"name\" as name, \n" +
            "fm.\"cost\" as cost, fm.description as description, INITCAP(fm.food_type) as \"foodType\",\n" +
            "(select fmp.id  from food_menu_picture fmp where fmp.is_active and fmp.food_menu_id = fm.id) as \"photoId\",\n" +
            "fm.is_available_today as \"isAvailableToday\", \n" +
            "case when fm.is_auto is true then (select coalesce (sum(imm.remaining_quantity), 0) as \"remainingQuantity\"  from inventory_menu_mapping imm where imm.food_menu_id = fm.id) else null end as \"remQuantity\" \n" +
            "from food_menu fm where fm.is_active and  \n" +
            "case when ?1='-1' then true else fm.\"name\" ilike concat('%',?1,'%') end and \n" +
            "case when ?2 is null then true \n" +
            "when ?2 = 'AUTO' then fm.is_auto is true else fm.food_type = ?2 end and \n" +
            "case when ?3 is null then true else fm.is_available_today = ?3 end order by fm.last_modified_date desc",
    countQuery = "select count(*) from (\n" +
            "select fm.id as id, fm.\"name\" as name, fm.\"cost\" as cost, fm.description as description, INITCAP(fm.food_type) as \"foodType\",\n" +
            "(select fmp.id  from food_menu_picture fmp where fmp.is_active and fmp.food_menu_id = fm.id) as \"photoId\",\n" +
            "fm.is_available_today as \"isAvailableToday\" \n" +
            "from food_menu fm where fm.is_active and  \n" +
            "case when ?1='-1' then true else fm.\"name\" ilike concat('%',?1,'%') end  and \n" +
            "case when ?2 is null then true \n" +
            "when ?2 = 'AUTO' then fm.is_auto is true else fm.food_type = ?2 end and \n" +
            "case when ?3 is null then true else fm.is_available_today = ?3  end order by fm.last_modified_date desc) foo")
    Page<Map<String, Object>> getFoodMenuPageable(String name, String foodType, Boolean filter, Pageable pageable);
}
