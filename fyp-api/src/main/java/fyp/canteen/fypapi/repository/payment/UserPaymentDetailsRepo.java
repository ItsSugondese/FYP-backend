package fyp.canteen.fypapi.repository.payment;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.payment.UserPaymentDetails;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserPaymentDetailsRepo extends GenericSoftDeleteRepository<UserPaymentDetails, Long> {


    @Query(nativeQuery = true, value = "select  * from user_payment_details upd  \n" +
            "where upd.pay_status in ('UNPAID', 'PARTIAL_PAID') and upd.user_id = ?1")
    List<UserPaymentDetails> getRemainingToPayTransactionOfUserByUserId(Long id);

    @Query(nativeQuery = true, value = "select coalesce ((select sum(foo.due_amount) from (select upd.onsite_order_id as orderId  from user_payment_details upd\n" +
            "join onsite_order oo on oo.id = upd.onsite_order_id  \n" +
            "where oo.user_id = ?1 and  oo.pay_status in ('UNPAID', 'PARTIAL_PAID') group by upd.onsite_order_id ) aa\n" +
            "join lateral (select upd2.*  from user_payment_details upd2 \n" +
            "order by created_date desc limit 1)\n" +
            "foo on foo.onsite_order_id = aa.orderId), 0)")
    double getTotalRemainingAmountToPayOfUserByUserId(Long id);


}
