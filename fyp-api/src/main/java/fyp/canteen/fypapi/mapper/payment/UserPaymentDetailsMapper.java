package fyp.canteen.fypapi.mapper.payment;

import fyp.canteen.fypcore.model.entity.payment.UserPaymentDetails;
import fyp.canteen.fypcore.pojo.payment.PaymentDetailsDataPojo;
import fyp.canteen.fypcore.pojo.payment.PaymentHistoryOfOrderResponsePojo;
import org.apache.ibatis.annotations.*;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Mapper
public interface UserPaymentDetailsMapper {

    @Select( "select foo.*, foo.onsite_order_id as orderId from (select upd.onsite_order_id as orderId  from user_payment_details upd\n" +
            "join onsite_order oo on oo.id = upd.onsite_order_id  \n" +
            "where  oo.pay_status = 'PARTIAL_PAID' and oo.user_id = #{id} group by upd.onsite_order_id ) aa\n" +
            "join lateral (select upd2.*  from user_payment_details upd2 \n" +
            "order by created_date desc limit 1)\n" +
            "foo on foo.onsite_order_id = aa.orderId")
    @Results({
            @Result(property = "order", column = "orderId",
            one = @One(select = "fyp.canteen.fypapi.mapper.ordermgmt.OnsiteOrderMapper.getById"))
    })
    List<PaymentDetailsDataPojo> getRemainingToPayTransactionOfUserByUserId(Long id);


    @Select("select to_char(upd.created_date, 'YYYY-MM-DD HH:MI AM') as \"paidDate\", \n" +
            "total_amount as \"remainingAmount\", paid_amount as \"paidAmount\", due_amount as \"dueAmount\" \n" +
            "from user_payment_details upd where upd.onsite_order_id  = #{id}")
    List<PaymentHistoryOfOrderResponsePojo> getOrderPaymentHistory(Long id);
}
