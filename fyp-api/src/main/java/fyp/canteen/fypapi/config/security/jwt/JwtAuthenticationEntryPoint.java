package fyp.canteen.fypapi.config.security.jwt;

import fyp.canteen.fypcore.exception.CustomSecurityException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
//@RestControllerAdvice
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint   {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        CustomSecurityException.unAuthorizedException(authException.getMessage(), response);
    }



}
