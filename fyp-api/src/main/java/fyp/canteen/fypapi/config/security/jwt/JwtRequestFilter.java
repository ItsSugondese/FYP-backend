package fyp.canteen.fypapi.config.security.jwt;

import fyp.canteen.fypapi.config.security.CustomUserDetailsService;
import fyp.canteen.fypcore.exception.CustomSecurityException;
import fyp.canteen.fypcore.utils.UserDataConfig;
import fyp.canteen.fypcore.config.security.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter  {

    private final  JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserDataConfig userDataConfig;

    private final RequestMatcher requestMatcher = new OrRequestMatcher(
            new AntPathRequestMatcher("/authenticate"),
            new AntPathRequestMatcher("/addUser"),
            new AntPathRequestMatcher("/canteen/login-with-google"),
            new AntPathRequestMatcher("/auth/**"),
            new AntPathRequestMatcher("/test/**"),
            new AntPathRequestMatcher("/test"),
            new AntPathRequestMatcher("/auth/validate-token"),
            new AntPathRequestMatcher("/auth/reset-password"),
            new AntPathRequestMatcher("/report/**"),
            new AntPathRequestMatcher("/auth/forgot-password"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/ws/**"),
            new AntPathRequestMatcher("/ws")
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!this.requestMatcher.matches(request)) {
            final String requestTokenHeader = request.getHeader("Authorization");

//            if(requestTokenHeader != null){
            String email = null;
            String jwtToken = null;

            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);

                try {
                    email = userDataConfig.getEmailFromToken(jwtToken);
                } catch (IllegalArgumentException e) {
                    CustomSecurityException.forbiddenException("Unable to get JWT Token", response);
                } catch (ExpiredJwtException e) {
                    CustomSecurityException.forbiddenException("JWT Token has expired", response);
                }
            } else {
                CustomSecurityException.forbiddenException("JWT token didn't start with Bearer", response);
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                if (jwtUtil.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
//        }

    }
        filterChain.doFilter(request, response);
    }

}
