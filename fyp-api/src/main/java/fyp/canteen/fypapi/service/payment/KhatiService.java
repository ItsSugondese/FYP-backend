package fyp.canteen.fypapi.service.payment;

import fyp.canteen.fypcore.pojo.payment.KhaltiTransactionVerificationRequestPojo;

public interface KhatiService {

    Object verifyTransaction(KhaltiTransactionVerificationRequestPojo requestPojo);
}
