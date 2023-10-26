package fyp.canteen.fypapi.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import fyp.canteen.fypapi.exception.CustomRestExceptionHandler;
import fyp.canteen.fypcore.enums.ResponseStatus;
import fyp.canteen.fypcore.pojo.ApiError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@Component
@RestControllerAdvice
public class JwtAuthenticationEntryPoint extends ResponseEntityExceptionHandler implements AuthenticationEntryPoint   {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final ApiError apiError = new ApiError(ResponseStatus.FAIL, httpStatus.value(), authException.getMessage(), authException.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), apiError);
    }



}
