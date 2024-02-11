package fyp.canteen.fypapi.mapper.ordermgmt;

import fyp.canteen.fypcore.model.entity.ordermgmt.OnsiteOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

@Mapper
public interface OnsiteOrderMapper {

    @Select("select *, oo.user_id as userId from onsite_order oo where oo.id = #{id}")
    @Results({
            @Result(property = "user", column = "userId",
                    one = @One(select = "fyp.canteen.fypapi.mapper.usermgmt.UserDetailMapper.getById"))
    })
    Optional<OnsiteOrder> getById(@Param("id") Long id);
}
