package fyp.canteen.fypapi.config.security;
import java.util.HashSet;
import java.util.Set;

import fyp.canteen.fypapi.repository.usermgmt.UserRepo;
import fyp.canteen.fypapi.service.usermgmt.UserService;
import fyp.canteen.fypcore.model.entity.usermgmt.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
    private final UserRepo userRepo;
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);

        if(user != null) {
            return  new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    getAuthority(user)
            );
        }else {
            throw new UsernameNotFoundException("User with that email '" + email + "' doesn't exist");
        }
    }

    private Set getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        });

        return authorities;
    }
}
