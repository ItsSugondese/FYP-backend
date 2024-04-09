package fyp.canteen.fypapi.service.inventory;

import fyp.canteen.fypcore.model.entity.inventory.InventoryMenuMapping;
import fyp.canteen.fypcore.pojo.inventory.InventoryFoodPaginationRequest;
import fyp.canteen.fypcore.pojo.inventory.InventoryMenuRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;

public interface InventoryMenuMappingService {

    void saveInventoryMenuMapping(InventoryMenuRequestPojo requestPojo);

    InventoryMenuMapping findById(Long id);
    PaginationResponse getInventoryMenuMappingPaginated(PaginationRequest request);
    PaginationResponse getInventoryDataOfFoodPaginated(InventoryFoodPaginationRequest request);
    void deleteInventoryLogById(Long id);
}
