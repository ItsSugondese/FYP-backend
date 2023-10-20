package fyp.canteen.fypapi.service.usermgmt;

import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.pojo.usermgmt.UserDetailsRequestPojo;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();

    @Override
    public void saveUser(UserDetailsRequestPojo requestPojo) {

    }

    @Override
    public void saveUserFromGoogleLogin(UserDetailsRequestPojo requestPojo) {
        User user = userRepo.findByEmail(requestPojo.getEmail()).orElse(new User());

        try {
            beanUtilsBean.copyProperties(user, requestPojo);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }

        userRepo.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new AppException("User with that email doesn't exists"));
    }

    @Override
    public User findUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new AppException(Message.IdNotFound(ModuleNameConstants.USER)));
    }
}
