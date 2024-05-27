package fyp.canteen.fypapi.service.jwt;


import fyp.canteen.fypapi.config.security.CustomUserDetailsService;
import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypapi.service.usermgmt.UserService;
import fyp.canteen.fypcore.config.security.JwtUtil;
import fyp.canteen.fypcore.enums.Device;
import fyp.canteen.fypcore.enums.UserType;
import fyp.canteen.fypcore.exception.AppException;
import fyp.canteen.fypcore.model.auth.Role;
import fyp.canteen.fypcore.pojo.jwt.JwtRequest;
import fyp.canteen.fypcore.pojo.jwt.JwtResponse;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class JwtService {

    private final CustomUserDetailsService userDetailsService;
    private final UserRepo userRepo;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;


    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userEmail = jwtRequest.getUserEmail().trim();
        String userPassword = jwtRequest.getUserPassword();

        authenticate(userEmail, userPassword);

        User user = userService.findUserByEmail(userEmail);

//        if(jwtRequest.getDevice().equals(Device.PHONE)){
//            if(user.getUserType().equals(UserType.ADMIN))
//                throw new AppException("Only users and staff are eligible to use the app");
//        }

        if(!user.isAccountNonLocked() && !user.getUserType().equals(UserType.ADMIN))
            throw new AppException("The account disabled by admin. Please contact the operator.");

        String newGeneratedToken = jwtUtil.generateToken(userDetailsService.loadUserByUsername(userEmail),
                user);


        return  JwtResponse.builder()
                .jwtToken(newGeneratedToken)
                .roles(user.getRole().stream().map(
                        Role::getRole
                ).collect(Collectors.toList()))
                .userId(user.getId())
                .email(user.getEmail())
                .username(user.getFullName())
                .build();
    }

    private void authenticate(String userEmail, String userPassword) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, userPassword));
        }catch (DisabledException e) {
            throw new AppException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new AppException("INVALID_CREDENTIALS", e);
        }
    }
}
