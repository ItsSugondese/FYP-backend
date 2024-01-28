package fyp.canteen.fypapi.service.usermgmt;

import fyp.canteen.fypapi.mapper.temporaryattachments.TemporaryAttachmentsDetailMapper;
import fyp.canteen.fypapi.mapper.usermgmt.StaffDetailMapper;
import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypapi.service.auth.RoleService;
import fyp.canteen.fypapi.service.resetpassword.ResetPasswordService;
import fyp.canteen.fypapi.utils.email.EmailServiceHelper;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.enums.UserType;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import fyp.canteen.fypcore.pojo.temporaryattachments.TemporaryAttachmentsDetailResponsePojo;
import fyp.canteen.fypcore.pojo.usermgmt.StaffDetailPaginationRequest;
import fyp.canteen.fypcore.pojo.usermgmt.StaffDetailResponsePojo;
import fyp.canteen.fypcore.pojo.usermgmt.StaffDetailsRequestPojo;
import fyp.canteen.fypcore.utils.NullAwareBeanUtilsBean;
import fyp.canteen.fypcore.utils.genericfile.FilePathConstants;
import fyp.canteen.fypcore.utils.genericfile.FilePathMapping;
import fyp.canteen.fypcore.utils.genericfile.GenericFileUtil;
import fyp.canteen.fypcore.utils.pagination.CustomPaginationHandler;
import fyp.canteen.fypcore.utils.pagination.PaginationResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImpl extends  UserServiceHelperImpl implements StaffService {
    private final UserRepo userRepo;
    private final BeanUtilsBean beanUtilsBean = new NullAwareBeanUtilsBean();
    private final TemporaryAttachmentsDetailMapper temporaryAttachmentsDetailMapper;
    private final CustomPaginationHandler customPaginationHandler;
    private final GenericFileUtil genericFileUtil;
    private final StaffDetailMapper staffDetailMapper;

    public StaffServiceImpl(RoleService roleService, UserRepo userRepo,
                            ResetPasswordService resetPasswordService,
                            TemporaryAttachmentsDetailMapper temporaryAttachmentsDetailMapper,
                            CustomPaginationHandler customPaginationHandler,
                            GenericFileUtil genericFileUtil, StaffDetailMapper staffDetailMapper,
                            EmailServiceHelper emailServiceHelper) {
        super(roleService, userRepo, resetPasswordService, emailServiceHelper);
        this.userRepo = userRepo;
        this.temporaryAttachmentsDetailMapper = temporaryAttachmentsDetailMapper;
        this.customPaginationHandler = customPaginationHandler;
        this.genericFileUtil = genericFileUtil;
        this.staffDetailMapper = staffDetailMapper;
    }

    @Override
    public void saveStaff(StaffDetailsRequestPojo requestPojo) {
        User user = new User();
        boolean alreadyExists;
        if(requestPojo.getId() != null)
            user = userRepo.findById(requestPojo.getId()).orElse(user);

        alreadyExists = staffValidation(requestPojo, user);

        try{
            beanUtilsBean.copyProperties(user, requestPojo);
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }

        if(!alreadyExists){
            user.setRole(getRoles(ModuleNameConstants.STAFF.toUpperCase()));
            user.setUserType(UserType.STAFF);
        }

        if(requestPojo.getProfileId() != null){
            user.setProfilePath(savePictureToPath(requestPojo.getProfileId()));
        }

        userRepo.save(user);
    }

    @Override
    public PaginationResponse getAllStaffPaginated(StaffDetailPaginationRequest paginationRequest) {
        return customPaginationHandler.getPaginatedData(userRepo.findAllStaff(paginationRequest.getPageable()));
    }

    @Override
    public StaffDetailResponsePojo getSingleStaffById(Long id) {
        return staffDetailMapper.getStaffDetailById(id).orElseThrow(() -> new AppException(Message.idNotFound(ModuleNameConstants.STAFF)));
    }

    @Override
    public void getStaffPhoto(HttpServletResponse response, Long id) {
        String photoPath = userRepo.findById(id).orElseThrow(() -> new RuntimeException("Staff not found")).getProfilePath();
        try {
            genericFileUtil.getFileFromFilePath(photoPath, response);
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }
    }


    private boolean staffValidation(StaffDetailsRequestPojo requestPojo, User user) {
        boolean exists = false;

        if(user.getId() == null || requestPojo.getId() == null){
            checkForUniqueEmail(requestPojo.getEmail());
        }else {
            exists = true;
        }
        return exists;
    }

    private String savePictureToPath(Long id){
        try {
            TemporaryAttachmentsDetailResponsePojo temporaryAttachmentsById = temporaryAttachmentsDetailMapper.getTemporaryAttachmentsById(id);
            return genericFileUtil.copyFileToServer(temporaryAttachmentsById.getLocation(), FilePathMapping.USER_FILE, FilePathConstants.TEMP_PATH);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
