package fyp.canteen.fypapi.repository.ordermgmt;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.ordermgmt.OrderUserMapping;

public interface OrderUserMappingRepo extends GenericSoftDeleteRepository<OrderUserMapping, Long> {
}
