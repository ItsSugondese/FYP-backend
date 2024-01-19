package fyp.canteen.fypapi.mapper.usermgmt;

import fyp.canteen.fypcore.pojo.usermgmt.StaffDetailResponsePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface StaffDetailMapper {

    @Select("select u.id, u.full_name as \"fullName\", u.account_non_locked as \"accountNonLocked\", u.email, \n" +
            "to_char(u.created_date, 'YYYY-MM-DD HH:MI AM') as \"startedWorkingOn\", u.contact_number as \"contactNumber\" from users u where u.id = #{id}")
    Optional<StaffDetailResponsePojo> getStaffDetailById(Long id);
}
