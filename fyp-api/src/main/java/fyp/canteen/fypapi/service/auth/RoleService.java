package fyp.canteen.fypapi.service.auth;

import fyp.canteen.fypcore.model.auth.Role;

public interface RoleService {

    Role findRoleByName(String name);
}
