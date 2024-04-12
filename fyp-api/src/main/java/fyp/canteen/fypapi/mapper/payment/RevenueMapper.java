package fyp.canteen.fypapi.mapper.payment;

import fyp.canteen.fypcore.pojo.dashboard.data.RevenueDataPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;

@Mapper
public interface RevenueMapper {

    @Select("select \n" +
            "coalesce (sum(whole.\"dueAmount\"), 0) as \"leftToPay\", coalesce(sum(whole.\"totalTransaction\"), 0)as \"revenue\", \n" +
            "coalesce (sum(whole.\"totalPaid\"), 0) as \"paidAmount\" , coalesce (sum(whole.delivered), 0) as \"deliveredOrder\"\n" +
            "from (\n" +
            "\tselect sum(suu.\"dueAmount\") as \"dueAmount\", suu.user_id, sum(suu.\"paidAmount\") as \"totalPaid\",\n" +
            "\t (sum(suu.\"dueAmount\") + sum(suu.\"paidAmount\")) as \"totalTransaction\", sum(suu.delivered) as delivered from (\n" +
            "\t\tselect sum(case when foo.row_num = 1 then foo.due_amount end) as \"dueAmount\", sum(foo.paid_amount) as \"paidAmount\", \n" +
            "\t\tfoo.user_id, sum(case when foo.row_num = 1 then 1 else 0 end) as delivered  from (\n" +
            "\t\t\tselect  oo.id as orderId  from onsite_order oo  \n" +
            "\t\t\twhere oo.pay_status = 'PARTIAL_PAID' \n" +
            "\t\t\tand cast(oo.created_date as date) between  cast(#{fromDate} as date)  and cast(#{toDate} as date)  \n" +
            "\t\t) aa\n" +
            "\t\t\tjoin lateral \n" +
            "\t\t\t\t(select upd2.*, ROW_NUMBER() OVER (ORDER BY created_date DESC) AS row_num from user_payment_details upd2 where upd2.onsite_order_id = aa.orderId \n" +
            "\t\t\t\torder by created_date desc \n" +
            "\t\t) foo on foo.onsite_order_id = aa.orderId\n" +
            " \t\tgroup by foo.user_id\n" +
            "\t\tunion\n" +
            "\t\tselect sum(case when oo2.pay_status = 'UNPAID' then oo2.total_price else 0 end) as \"dueAmount\",   \n" +
            "\t\tsum(case when oo2.pay_status = 'PAID' then oo2.total_price else 0 end) as \"paidAmount\", oo2.user_id,\n" +
            "\t\tcount(*) as delivered\n" +
            "\t\tfrom onsite_order oo2 \n" +
            "\t\twhere oo2.is_active and  oo2.pay_status in ('PAID', 'UNPAID')   and oo2.approval_status = 'DELIVERED' \n" +
            "\t\tand cast(oo2.created_date as date) between  cast(#{fromDate} as date)  and cast(#{toDate} as date) \n" +
            "\t\tgroup by oo2.user_id\n" +
            "\t) suu \n" +
            "\tgroup by suu.user_id\n" +
            ") whole")
    RevenueDataPojo revenueAmountStatistics(@Param("fromDate") LocalDate fromDate,
                                            @Param("toDate") LocalDate toDate);
}
