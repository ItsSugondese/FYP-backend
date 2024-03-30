package fyp.canteen.fypapi.service.inventory;

import fyp.canteen.fypcore.pojo.inventory.InventoryMenuRequestPojo;
import fyp.canteen.fypcore.utils.pagination.PaginationRequest;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;

public interface InventoryMenuMappingService {

    void saveInventoryMenuMapping(InventoryMenuRequestPojo requestPojo);

    PaginationResponse getInventoryMenuMappingPaginated(PaginationRequest request);
}
