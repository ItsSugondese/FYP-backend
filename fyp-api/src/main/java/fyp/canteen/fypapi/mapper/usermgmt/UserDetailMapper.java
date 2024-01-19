package fyp.canteen.fypapi.mapper.usermgmt;

import fyp.canteen.fypcore.pojo.usermgmt.UserDetailResponsePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserDetailMapper {

    @Select("select u.id, u.full_name as \"fullName\", u.account_non_locked as \"accountNonLocked\", u.email, \n" +
            "u.profile_path as \"profilePath\" from users u where u.id = #{id}")
    Optional<UserDetailResponsePojo> getSingleUser(Long id);
}
