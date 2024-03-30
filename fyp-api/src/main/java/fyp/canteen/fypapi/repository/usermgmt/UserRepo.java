package fyp.canteen.fypapi.repository.usermgmt;

import fyp.canteen.fypcore.generics.api.GenericSoftDeleteRepository;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepo extends GenericSoftDeleteRepository<User, Long> {

    @Query(value = "select case  when\n" +
            "    upper(email) = ?1 then 'Email Address Already Exists' end as \"Status\"\n" +
            "    from users order by \"Status\" limit 1", nativeQuery = true)
    String isUnique(String username);
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);


    @Query(nativeQuery = true,
            value = "select * from(select u.id, INITCAP(u.full_name) as \"fullName\", u.account_non_locked as \"accountNonLocked\", u.email, \n" +
                    "u.profile_path as \"profilePath\", u.contact_number as \"contactNumber\", \n" +
                    "case when u.user_type = 'EXTERNAL_USER' then true else false end as \"isExternal\"\n" +
                    "from users u where u.is_active and u.user_type in ?1 and \n" +
                    "case when ?2 = '-1' then true else u.full_name ilike concat('%',?2,'%') end \n" +
                    "order by u.last_modified_date desc) parent\n" +
                    "join lateral (\n" +
                    "select coalesce ((select sum(totalSum) from ((select sum(foo.due_amount) as totalSum from \n" +
                    "(select  upd.onsite_order_id as orderId  from user_payment_details upd\n" +
                    "join onsite_order oo on oo.id = upd.onsite_order_id  \n" +
                    "where oo.user_id =  parent.id and oo.approval_status = 'DELIVERED' and  oo.pay_status = 'PARTIAL_PAID'  group by upd.onsite_order_id ) aa\n" +
                    "join lateral (select upd2.*  from user_payment_details upd2 where upd2.onsite_order_id = aa.orderId \n" +
                    "order by created_date desc limit 1\n" +
                    ") \n" +
                    "foo on foo.onsite_order_id = aa.orderId)\n" +
                    "union\n" +
                    "(select sum(oo2.total_price) as totalSum from onsite_order oo2 where oo2.user_id  = parent.id and oo2.is_active  and oo2.pay_status = 'UNPAID' and oo2.approval_status = 'DELIVERED')\n" +
                    ") foo), 0) as \"remainingAmount\") child on true\n" +
                    "where case \n" +
                    "\twhen ?3 = 'PAID' then child.\"remainingAmount\" = 0\n" +
                    "\twhen ?3 = 'UNPAID' then child.\"remainingAmount\" > 0\n" +
                    "\telse true\n" +
                    "end",
     countQuery= "select count(*) from (select * from(select u.id, INITCAP(u.full_name) as \"fullName\", u.account_non_locked as \"accountNonLocked\", u.email, \n" +
             "u.profile_path as \"profilePath\", u.contact_number as \"contactNumber\", \n" +
             "case when u.user_type = 'EXTERNAL_USER' then true else false end as \"isExternal\"\n" +
             "from users u where u.is_active and u.user_type in ?1 and \n" +
             "case when ?2 = '-1' then true else u.full_name ilike concat('%',?2,'%') end \n" +
             "order by u.last_modified_date desc) parent\n" +
             "join lateral (\n" +
             "select coalesce ((select sum(totalSum) from ((select sum(foo.due_amount) as totalSum from \n" +
             "(select  upd.onsite_order_id as orderId  from user_payment_details upd\n" +
             "join onsite_order oo on oo.id = upd.onsite_order_id  \n" +
             "where oo.user_id =  parent.id and  oo.pay_status = 'PARTIAL_PAID'  group by upd.onsite_order_id ) aa\n" +
             "join lateral (select upd2.*  from user_payment_details upd2 where upd2.onsite_order_id = aa.orderId \n" +
             "order by created_date desc limit 1\n" +
             ") \n" +
             "foo on foo.onsite_order_id = aa.orderId)\n" +
             "union\n" +
             "(select sum(oo2.total_price) as totalSum from onsite_order oo2 where oo2.user_id  = parent.id and oo2.is_active  and oo2.pay_status = 'UNPAID' and oo2.approval_status = 'DELIVERED')\n" +
             ") foo), 0) as \"remainingAmount\") child on true\n" +
             "where case \n" +
             "\twhen ?3 = 'PAID' then child.\"remainingAmount\" = 0\n" +
             "\twhen ?3 = 'UNPAID' then child.\"remainingAmount\" > 0\n" +
             "\telse true\n" +
             "end) foo")
    Page<Map<String, Object>> findAllUsers(List<String> userType, String name, String payStatus, Pageable pageable);

    @Query(nativeQuery = true, value = "select u.id, INITCAP(u.full_name) as \"fullName\", u.account_non_locked as \"accountNonLocked\", u.email, \n" +
            "u.profile_path as \"profilePath\", u.contact_number as \"contactNumber\" from users u where u.is_active and u.user_type = 'STAFF'")
    Page<Map<String, Object>> findAllStaff(Pageable pageable);

    @Query(nativeQuery = true,
    value = "select u.id from users u  where u.user_type <> 'EXTERNAL_USER' and \n" +
            "    case when cast(?1 as int) is null then true else u.id <> ?1 end")
    List<Long> getAllIdOfUsers(Long ignoreId);


    @Query(nativeQuery = true, value = "select \n" +
            "whole.* \n" +
            "from (\n" +
            "\tselect sum(suu.\"dueAmount\") as \"dueAmount\", suu.user_id, initcap( suu.full_name) as \"fullName\",\n" +
            "\tsum(suu.\"paidAmount\") as \"totalPaid\",\n" +
            "\t (sum(suu.\"dueAmount\") + sum(suu.\"paidAmount\")) as \"totalTransaction\", \n" +
            "ROW_NUMBER() OVER (ORDER BY  sum(suu.\"paidAmount\") desc) AS sno from (\n" +
            "\t\tselect sum(case when foo.row_num = 1 then foo.due_amount end) as \"dueAmount\", sum(foo.paid_amount) as \"paidAmount\", \n" +
            "\t\tfoo.user_id, foo.full_name  from (\n" +
            "\t\t\tselect  upd.onsite_order_id as orderId  from user_payment_details upd\n" +
            "\t\t\tjoin onsite_order oo on oo.id = upd.onsite_order_id  \n" +
            "\t\t\twhere oo.pay_status = 'PARTIAL_PAID' \n" +
            "\t\t\tand cast(oo.created_date as date) between  ?1 and ?2  \n" +
            "\t\t\tgroup by upd.onsite_order_id \n" +
            "\t\t) aa\n" +
            "\t\t\tjoin lateral \n" +
            "\t\t\t\t(select upd2.*, u.full_name , ROW_NUMBER() OVER (ORDER BY upd2.created_date DESC) AS row_num from user_payment_details upd2 \n" +
            "\t\t\t\tjoin users u on u.id = upd2.user_id \n" +
            "\t\t\t\twhere upd2.onsite_order_id = aa.orderId\n" +
            "\t\t\t\torder by created_date desc \n" +
            "\t\t) foo on foo.onsite_order_id = aa.orderId\n" +
            " \t\tgroup by foo.user_id, foo.full_name\n" +
            "\t\tunion\n" +
            "\t\tselect sum(case when oo2.pay_status = 'UNPAID' then oo2.total_price else 0 end) as \"dueAmount\",   \n" +
            "\t\tsum(case when oo2.pay_status = 'PAID' then oo2.total_price else 0 end) as \"paidAmount\", oo2.user_id ,\n" +
            "\t\tu2.full_name \n" +
            "\t\tfrom onsite_order oo2  join users u2 on u2.id  = oo2.user_id \n" +
            "\t\twhere oo2.is_active and  oo2.pay_status in ('PAID', 'UNPAID')   and oo2.approval_status = 'DELIVERED'  \n" +
            "\t\tand cast(oo2.created_date as date) between  ?1 and ?2 \n" +
            "\t\tgroup by oo2.user_id, u2.full_name \n" +
            "\t) suu \n" +
            "\tgroup by suu.user_id, suu.full_name \n" +
            ") whole \n" +
            "where case when ?3 = '-1' then true else whole.\"fullName\"  ilike concat('%', ?3, '%') end order by whole.\"totalPaid\" desc",
    countQuery = "select count(*) from (\n" +
            "select \n" +
            "whole.* \n" +
            "from (\n" +
            "\tselect sum(suu.\"dueAmount\") as \"dueAmount\", suu.user_id, initcap( suu.full_name) as \"fullName\",\n" +
            "\tsum(suu.\"paidAmount\") as \"totalPaid\",\n" +
            "\t (sum(suu.\"dueAmount\") + sum(suu.\"paidAmount\")) as \"totalTransaction\", \n" +
            "ROW_NUMBER() OVER (ORDER BY  sum(suu.\"paidAmount\") desc) AS sno from (\n" +
            "\t\tselect sum(case when foo.row_num = 1 then foo.due_amount end) as \"dueAmount\", sum(foo.paid_amount) as \"paidAmount\", \n" +
            "\t\tfoo.user_id, foo.full_name  from (\n" +
            "\t\t\tselect  upd.onsite_order_id as orderId  from user_payment_details upd\n" +
            "\t\t\tjoin onsite_order oo on oo.id = upd.onsite_order_id  \n" +
            "\t\t\twhere oo.pay_status = 'PARTIAL_PAID' \n" +
            "\t\t\tand cast(oo.created_date as date) between  ?1 and ?2  \n" +
            "\t\t\tgroup by upd.onsite_order_id \n" +
            "\t\t) aa\n" +
            "\t\t\tjoin lateral \n" +
            "\t\t\t\t(select upd2.*, u.full_name , ROW_NUMBER() OVER (ORDER BY upd2.created_date DESC) AS row_num from user_payment_details upd2 \n" +
            "\t\t\t\tjoin users u on u.id = upd2.user_id \n" +
            "\t\t\t\twhere upd2.onsite_order_id = aa.orderId\n" +
            "\t\t\t\torder by created_date desc \n" +
            "\t\t) foo on foo.onsite_order_id = aa.orderId\n" +
            " \t\tgroup by foo.user_id, foo.full_name\n" +
            "\t\tunion\n" +
            "\t\tselect sum(case when oo2.pay_status = 'UNPAID' then oo2.total_price else 0 end) as \"dueAmount\",   \n" +
            "\t\tsum(case when oo2.pay_status = 'PAID' then oo2.total_price else 0 end) as \"paidAmount\", oo2.user_id ,\n" +
            "\t\tu2.full_name \n" +
            "\t\tfrom onsite_order oo2  join users u2 on u2.id  = oo2.user_id \n" +
            "\t\twhere oo2.is_active and  oo2.pay_status in ('PAID', 'UNPAID')   and oo2.approval_status = 'DELIVERED'  \n" +
            "\t\tand cast(oo2.created_date as date) between  ?1 and ?2 \n" +
            "\t\tgroup by oo2.user_id, u2.full_name \n" +
            "\t) suu \n" +
            "\tgroup by suu.user_id, suu.full_name \n" +
            ") whole \n" +
            "where case when ?3 = '-1' then true else whole.\"fullName\"  ilike concat('%', ?3, '%') end order by whole.\"totalPaid\" desc\n) foo")
    Page<Map<String, Object>> getAllUserFinanceData(LocalDate fromDate, LocalDate toDate, String name, Pageable pageable);
}
