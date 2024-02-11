package fyp.canteen.fypapi.service.payment;

import fyp.canteen.fypcore.pojo.payment.UserPaymentDetailsRequestPojo;

public interface UserPaymentDetailsService {

    void savePayment(UserPaymentDetailsRequestPojo requestPojo);
}
