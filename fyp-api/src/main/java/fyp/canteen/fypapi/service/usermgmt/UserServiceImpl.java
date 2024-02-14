package fyp.canteen.fypapi.service.usermgmt;

import fyp.canteen.fypapi.mapper.temporaryattachments.TemporaryAttachmentsDetailMapper;
import fyp.canteen.fypapi.mapper.usermgmt.StaffDetailMapper;
import fyp.canteen.fypapi.mapper.usermgmt.UserDetailMapper;
import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypapi.service.auth.RoleService;
import fyp.canteen.fypapi.service.resetpassword.ResetPasswordService;
import fyp.canteen.fypapi.utils.email.EmailServiceHelper;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.UserType;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.auth.Role;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenu;
import fyp.canteen.fypcore.model.entity.foodmgmt.FoodMenuPicture;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.pojo.temporaryattachments.TemporaryAttachmentsDetailResponsePojo;
import fyp.canteen.fypcore.pojo.usermgmt.*;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import fyp.canteen.fypcore.utils.UserDataConfig;
import fyp.canteen.fypcore.utils.genericfile.FilePathConstants;
import fyp.canteen.fypcore.utils.genericfile.FilePathMapping;
import fyp.canteen.fypcore.utils.genericfile.GenericFileUtil;
import fyp.canteen.fypcore.utils.pagination.CustomPaginationHandler;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl extends UserServiceHelperImpl implements UserService{
    private final UserRepo userRepo;
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
    private final CustomPaginationHandler customPaginationHandler;
    private final UserDetailMapper userDetailMapper;
    private final UserDataConfig userDataConfig;

    public UserServiceImpl(RoleService roleService, UserRepo userRepo, ResetPasswordService resetPasswordService, EmailServiceHelper emailServiceHelper,
                           CustomPaginationHandler customPaginationHandler,
                           UserDetailMapper userDetailMapper, UserDataConfig userDataConfig) {
        super(roleService, userRepo, resetPasswordService, emailServiceHelper);
        this.userRepo = userRepo;
        this.customPaginationHandler = customPaginationHandler;
        this.userDetailMapper = userDetailMapper;
        this.userDataConfig = userDataConfig;
    }

    @Override
    public void saveUserFromGoogleLogin(UserDetailsRequestPojo requestPojo) {
        User user = userRepo.findByEmail(requestPojo.getEmail()).orElse(new User());

        try {
            beanUtilsBean.copyProperties(user, requestPojo);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }

        user.setRole(getRoles(ModuleNameConstants.USER.toUpperCase()));
        userRepo.save(user);
    }

    @Override
    public PaginationResponse getAllUsersPaginated(UserDetailPaginationRequest paginationRequest) {
        return customPaginationHandler.getPaginatedData(userRepo.findAllUsers(paginationRequest.getPageable()));
    }


    @Override
    public UserDetailResponsePojo getSingleUserById(Long id) {
        return userDetailMapper.getSingleUser(id).orElseThrow(() -> new AppException(Message.idNotFound(ModuleNameConstants.USER)));
    }

    @Override
    public UserDetailResponsePojo getSingleUserWithoutId() {
        return getSingleUserById(userDataConfig.userId());
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
