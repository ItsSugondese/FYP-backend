package fyp.canteen.fypapi.service.auth;

import fyp.canteen.fypcore.model.entity.auth.Role;

public interface RoleService {

    Role findRoleByName(String name);
}
