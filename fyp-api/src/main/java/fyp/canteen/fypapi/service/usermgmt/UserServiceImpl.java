package fyp.canteen.fypapi.service.usermgmt;

import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypapi.service.auth.RoleService;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.auth.Role;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.pojo.usermgmt.UserDetailPaginationRequest;
import fyp.canteen.fypcore.pojo.usermgmt.UserDetailResponsePojo;
import fyp.canteen.fypcore.pojo.usermgmt.UserDetailsRequestPojo;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import fyp.canteen.fypcore.utils.pagination.CustomPaginationHandler;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
    private final CustomPaginationHandler customPaginationHandler;

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

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findRoleByName("USER"));

        user.setRole(roles);
        userRepo.save(user);
    }

    @Override
    public PaginationResponse getAllUsersPaginated(UserDetailPaginationRequest paginationRequest) {
        return customPaginationHandler.getPaginatedData(userRepo.findAllUsers(paginationRequest.getPageable()));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new AppException("User with that email doesn't exists"));
    }

    @Override
    public User findUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new AppException(Message.idNotFound(ModuleNameConstants.USER)));
    }
}
