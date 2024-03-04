package fyp.canteen.fypapi.service.payment;

import fyp.canteen.fypcore.pojo.payment.PaymentHistoryOfOrderResponsePojo;
import fyp.canteen.fypcore.pojo.payment.UserPaymentDetailsRequestPojo;

import java.util.List;

public interface UserPaymentDetailsService {

    void savePayment(UserPaymentDetailsRequestPojo requestPojo);
    void payRemainingAmount(UserPaymentDetailsRequestPojo requestPojo);
    Double getRemainingAmountOfUser(Long id);
    List<PaymentHistoryOfOrderResponsePojo> getOrderHistoryOfUserByOrderId(Long id);
}
