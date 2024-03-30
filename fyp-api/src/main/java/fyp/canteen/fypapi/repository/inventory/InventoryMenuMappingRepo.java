package fyp.canteen.fypapi.repository.inventory;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.inventory.InventoryMenuMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface InventoryMenuMappingRepo extends GenericSoftDeleteRepository<InventoryMenuMapping, Long> {

    @Query(nativeQuery = true, value = "select * from (select fm.id, fm.\"name\" as name, \n" +
            "fm.\"cost\" as cost, fm.description as description, INITCAP(fm.food_type) as \"foodType\",\n" +
            "(select fmp.id  from food_menu_picture fmp where fmp.is_active and fmp.food_menu_id = fm.id) as \"photoId\" from food_menu fm where fm.is_auto ) parent \n" +
            "join lateral (\n" +
            "select coalesce (sum(imm.remaining_quantity), 0) as \"remainingQuantity\"  from inventory_menu_mapping imm where imm.food_menu_id = parent.id) child \n" +
            "on true",
    countQuery = "select count(*) from (\n" +
            "select * from (select fm.id, fm.\"name\" as name, \n" +
            "fm.\"cost\" as cost, fm.description as description, INITCAP(fm.food_type) as \"foodType\",\n" +
            "(select fmp.id  from food_menu_picture fmp where fmp.is_active and fmp.food_menu_id = fm.id) as \"photoId\" from food_menu fm where fm.is_auto ) parent \n" +
            "join lateral (\n" +
            "select coalesce (sum(imm.remaining_quantity), 0) as \"remainingQuantity\"  from inventory_menu_mapping imm where imm.food_menu_id = parent.id) child \n" +
            "on true) foo")
    Page<Map<String, Object>> getMenuMappingPaginated(Pageable pageable);
}
