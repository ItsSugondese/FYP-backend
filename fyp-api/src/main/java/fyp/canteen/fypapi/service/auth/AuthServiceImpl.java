package fyp.canteen.fypapi.service.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypapi.service.jwt.JwtService;
import fyp.canteen.fypapi.service.usermgmt.UserService;
import fyp.canteen.fypapi.exception.AppException;
import fyp.canteen.fypcore.enums.UserType;
import fyp.canteen.fypcore.pojo.jwt.JwtRequest;
import fyp.canteen.fypcore.pojo.jwt.JwtResponse;
import fyp.canteen.fypcore.pojo.usermgmt.UserDetailsRequestPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserRepo userRepo;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final String appClient = "746907184110-46l1lat1ds4e1fvk6vhd2dn9j8f97hp7.apps.googleusercontent.com";


    @Override
    public JwtResponse signInWithGoogle(String credential) {
        GoogleIdToken idToken = verifyAndGetGoogleIdToken(credential);

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String password = payload.getSubject();

            if (!userRepo.existsByEmail(email)) {
                userService.saveUserFromGoogleLogin(UserDetailsRequestPojo.builder()
                        .email(email)
                        .password(encoder.encode(password))
                        .userType(UserType.USER)
                        .isEmailAddressVerified(payload.getEmailVerified())
                        .fullName(payload.get("given_name") + " " + payload.get("family_name"))
                        .profilePath((String) payload.get("picture"))
                        .build());
            }
            try {
                // Use the user information to generate a JWT or perform other actions
                return generateToken(JwtRequest.builder().userEmail(email).userPassword(password).build());
            } catch (Exception e) {
                throw new AppException(e.getMessage(), e);
            }
        } else
            throw new AppException("Invalid credential");
    }




    @Override
    public JwtResponse signIn(JwtRequest request) {
        return generateToken(request);
    }

    private JwtResponse generateToken(JwtRequest request){
        try {
            // Use the user information to generate a JWT or perform other actions
            return jwtService.createJwtToken(JwtRequest.builder()
                    .userEmail(request.getUserEmail())
                    .userPassword(request.getUserPassword())
                    .build());
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    private GoogleIdToken verifyAndGetGoogleIdToken(String credential) {
        try {
            return googleIdTokenVerifier.verify(credential);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }


}
