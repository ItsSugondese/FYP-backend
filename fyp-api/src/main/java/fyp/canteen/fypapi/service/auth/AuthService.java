package fyp.canteen.fypapi.service.auth;

import fyp.canteen.fypcore.model.entity.auth.jwt.JwtRequest;
import fyp.canteen.fypcore.model.entity.auth.jwt.JwtResponse;

public interface AuthService {

    JwtResponse signInWithGoogle(String credential);

    JwtResponse signIn(JwtRequest request);
}
