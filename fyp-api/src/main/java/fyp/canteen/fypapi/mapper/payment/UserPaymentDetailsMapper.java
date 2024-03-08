package fyp.canteen.fypapi.mapper.payment;

import fyp.canteen.fypcore.pojo.dashboard.data.SalesDataPojo;
import fyp.canteen.fypcore.pojo.payment.PaymentDetailsDataPojo;
import fyp.canteen.fypcore.pojo.payment.PaymentHistoryOfOrderResponsePojo;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
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

    @Select(" SELECT \n" +
            "    quantity, \n" +
            "    \"name\", \n" +
            "    salesIncome \n" +
            "FROM (\n" +
            "    SELECT \n" +
            "        sum(child.quantity) AS quantity, \n" +
            "        child.\"name\", \n" +
            "        SUM(child.paid) AS salesIncome \n" +
            "    FROM (\n" +
            "        SELECT  \n" +
            "            upd.onsite_order_id AS ooi\n" +
            "        FROM \n" +
            "            user_payment_details upd \n" +
            "            JOIN onsite_order oo ON oo.id = upd.onsite_order_id \n" +
            "        WHERE \n" +
            "            oo.pay_status <> 'PARTIAL_PAID'\n" +
            "            AND CAST(upd.created_date AS DATE) BETWEEN CAST(#{fromDate} AS DATE) AND CAST(#{toDate} AS DATE)\n" +
            "        GROUP BY \n" +
            "            upd.onsite_order_id\n" +
            "    ) aa\n" +
            "    JOIN LATERAL (\n" +
            "        SELECT   \n" +
            "            ofm.onsite_order_id AS ooi, ofm.quantity , \n" +
            "            fm.id AS menuId, \n" +
            "            fm.\"name\", \n" +
            "            ofm.id, \n" +
            "            fm.\"cost\" AS paid \n" +
            "        FROM \n" +
            "            order_food_mapping ofm \n" +
            "            JOIN food_menu fm ON fm.id = ofm.food_id \n" +
            "        WHERE \n" +
            "            ofm.onsite_order_id = aa.ooi\n" +
            "    ) child ON aa.ooi = child.ooi\n" +
            "    GROUP BY  \n" +
            "        child.\"name\"\n" +
            ") AS subquery\n" +
            "ORDER BY \n" +
            "    CASE WHEN #{filterType} = 'SALES' THEN salesIncome ELSE quantity END DESC")
    List<SalesDataPojo.FoodSalesData> getSalesData(@Param("filterType") String filterType,
                               @Param("fromDate") LocalDate fromDate,
                               @Param("toDate") LocalDate toDate);
}
