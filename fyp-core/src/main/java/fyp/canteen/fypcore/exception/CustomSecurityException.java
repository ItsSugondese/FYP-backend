package fyp.canteen.fypcore.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import fyp.canteen.fypcore.enums.ResponseStatus;
import fyp.canteen.fypcore.pojo.ApiError;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

public class CustomSecurityException {

    public static void forbiddenException(String message, HttpServletResponse response) {
                    HttpStatus httpStatus = HttpStatus.FORBIDDEN;
            final ApiError apiError = new ApiError(ResponseStatus.FAIL, httpStatus.value(), message, message);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                objectMapper.writeValue(response.getWriter(), apiError);
            }catch (Exception e){
                throw new AppException(e.getMessage(), e);
            }
    }

    public static void unAuthorizedException(String message, HttpServletResponse response) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        final ApiError apiError = new ApiError(ResponseStatus.FAIL, httpStatus.value(), message, message);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(response.getWriter(), apiError);
        }catch (Exception e){
            throw new AppException(e.getMessage(), e);
        }
    }

}
