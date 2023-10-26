package fyp.canteen.fypapi.exception;

import fyp.canteen.fypcore.enums.ResponseStatus;
import fyp.canteen.fypcore.pojo.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({AppException.class})
    public ResponseEntity<Object> handleAppException(final RuntimeException ex, final WebRequest request) {
        logger.error(ex.getClass().getName());
        logger.error("error", ex);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final ApiError apiError = new ApiError(ResponseStatus.FAIL, httpStatus.value(), ex.getMessage(), ex.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), httpStatus);
    }
}
