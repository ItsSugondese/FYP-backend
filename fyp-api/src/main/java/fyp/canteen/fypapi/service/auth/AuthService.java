package fyp.canteen.fypapi.service.auth;

import fyp.canteen.fypcore.pojo.jwt.JwtRequest;
import fyp.canteen.fypcore.pojo.jwt.JwtResponse;

public interface AuthService {

    JwtResponse signInWithGoogle(String credential);

    JwtResponse signIn(JwtRequest request);
}
