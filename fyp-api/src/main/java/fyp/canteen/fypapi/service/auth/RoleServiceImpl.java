package fyp.canteen.fypapi.service.auth;

import fyp.canteen.fypapi.repository.auth.RoleRepo;
import fyp.canteen.fypcore.constants.Message;
import fyp.canteen.fypcore.constants.ModuleNameConstants;
import fyp.canteen.fypapi.exception.AppException;
import fyp.canteen.fypcore.model.entity.auth.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepo roleRepo;

    @Override
    public Role findRoleByName(String name) {
        return roleRepo.findById(name.toUpperCase()).orElseThrow(() -> new AppException(Message.IdNotFound(ModuleNameConstants.ROLE)));
    }
}
