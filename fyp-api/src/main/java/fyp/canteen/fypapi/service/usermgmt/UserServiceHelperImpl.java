package fyp.canteen.fypapi.service.usermgmt;

import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypapi.service.auth.RoleService;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.auth.Role;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class UserServiceHelperImpl{
    private final RoleService roleService;
    private final UserRepo userRepo;
    public void checkForUniqueEmail(String email) {
        String checkUnique = userRepo.isUnique(email.toUpperCase());
        if (checkUnique != null)
            throw new AppException(checkUnique);
    }

    public Set<Role> getRoles(String role){
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findRoleByName(role));
        return roles;
    }
}
