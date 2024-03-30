package fyp.canteen.fypapi.repository.inventory;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.inventory.InventoryLog;

public interface InventoryLogRepo extends GenericSoftDeleteRepository<InventoryLog, Long> {
}
