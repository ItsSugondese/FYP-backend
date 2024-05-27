package fyp.canteen.fypapi.config.websocket;

import fyp.canteen.fypcore.model.entity.usermgmt.User;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

@RequiredArgsConstructor
public class CustomPrincipal implements Principal {
    private final User user;

    public User getUser() {
        return user;
    }

    @Override
    public String getName() {
        // Assuming the user object has a username or some identifier
        return user.getFullName(); // Adjust this based on your User class
    }
}
