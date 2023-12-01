package fyp.canteen.fypapi.repository.payment;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.payment.UserPaymentDetails;

public interface UserPaymentDetailsRepo extends GenericSoftDeleteRepository<UserPaymentDetails, Long> {
}
