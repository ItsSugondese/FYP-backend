package fyp.canteen.fypcore.exception;

import fyp.canteen.fypcore.constants.ErrorMessages;
import fyp.canteen.fypcore.enums.ResponseStatus;
import fyp.canteen.fypcore.pojo.ApiError;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({AppException.class})
    public ResponseEntity<Object> handleAppException(final RuntimeException ex, final WebRequest request) {
        logger.error(ex.getClass().getName());
        logger.error("error", ex);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final ApiError apiError = new ApiError(ResponseStatus.FAIL, httpStatus.value(), ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), httpStatus);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(final AuthenticationException ex, final WebRequest request) {
        logger.error(ex.getClass().getName());
        logger.error("error", ex);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final ApiError apiError = new ApiError(ResponseStatus.FAIL, httpStatus.value(), ex.getMessage(), ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), httpStatus);
    }


    @ExceptionHandler({JpaSystemException.class})
    public ResponseEntity<Object> handleConstraintViolation(GenericJDBCException ex, WebRequest request) {
        logger.error("JpaSystemException Caught");
        String message = ex.getCause().toString();
        logger.error("message: {}", ex.getCause());
        if (message.contains("function_exception_")) {
            logger.error("postgres function exception");
            message = StringUtils.substringBetween(message, "function_exception_", "\n");
            message = message.replaceAll("_", " ");


        } else {
            logger.error(">>>>>>>>>>>>> Error not handled here so , Please contact operator >>>>>>>> ");
        }
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        logger.error(ex.getMessage());
        final ApiError apiError = new ApiError(ResponseStatus.FAIL, httpStatus.value(), message, message);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), httpStatus);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        logger.error(ex.getClass().getName());
        logger.error("error", ex);
        //
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        final ApiError apiError = new ApiError(ResponseStatus.FAIL, httpStatus.value(), ex.getMessage(), ex.getMessage());
        final ApiError apiError = new ApiError(ResponseStatus.FAIL, httpStatus.value(), "Something went wrong", ex.getLocalizedMessage());

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), httpStatus);
    }
}


//    @ExceptionHandler({DataIntegrityViolationException.class})
//    public ResponseEntity<Object> handleConstraintViolation(DataIntegrityViolationException ex, WebRequest request) {
//        //ex.printStackTrace();
//        final List<String> errors = new ArrayList<String>();
//        String fieldName = "";
//        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
//            org.hibernate.exception.ConstraintViolationException violation = ((org.hibernate.exception.ConstraintViolationException) ex.getCause());
//            if (violation.getConstraintName().contains("unique_")) {
//
//                errors.add(ErrorMessages.alreadyExistsError(violation.getConstraintName().replace("unique_", "")));
//            } else if (violation.getConstraintName().contains("_check"))
//                errors.add(ErrorMessages.checkConstraintsError(violation.getConstraintName().split("_check")[0]));
//            else if (violation.getCause().getLocalizedMessage().contains("not-null"))
//                errors.add(ErrorMessages.doesNotExistsError(violation.getConstraintName()));
//            else if (violation.getCause().getLocalizedMessage().contains("is not present in table"))
//                errors.add(ErrorMessages.doesNotExistsError(violation.getConstraintName().replace("fk_", "")));
//            else if (violation.getCause().getLocalizedMessage().contains("is still referenced")) {
//                String[] constraintName = violation.getConstraintName().split("_");
//
//                try {
//                    errors.add(ErrorMessages.couldNotDeleteError(constraintName[0]));
//
//                } catch (Exception e) {
//                    errors.add(ErrorMessages.couldNotDeleteError(ErrorMessages.usedInOtherLocation()));
//
//                }
//                //Matcher matcher = Pattern.compile("fk_(.*?)_[a-zA-Z]+_id").matcher(violation.getConstraintName());
//            } else
//                errors.add(ErrorMessages.databaseError());
//            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
//            final ApiError apiError = new ApiError(ResponseStatus.FAIL, httpStatus.value(), errors.get(0), errors);
//            return new ResponseEntity<Object>(apiError, new HttpHeaders(), httpStatus);
//        } else if (ex.getCause() instanceof org.hibernate.exception.DataException) {
//            org.hibernate.exception.DataException violation = ((org.hibernate.exception.DataException) ex.getCause());
//            if (violation.getCause().toString().contains("value too long for type character varying")) {
//                errors.add("Text length too long");
//            }
//            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
//            final ApiError apiError = new ApiError(ResponseStatus.FAIL, httpStatus.value(), errors.get(0), errors);
//            return new ResponseEntity<Object>(apiError, new HttpHeaders(), httpStatus);
//        } else if (ex.getCause() instanceof GenericJDBCException) {
//            GenericJDBCException e = (GenericJDBCException) ex.getCause();
//            String message = e.getMessage();
//            if (message.contains("function_exception_")) {
//                message = message.replaceAll("function_exception_", "");
//                message = message.replaceAll("_", " ");
//                errors.add(message);
//            }
//        }
//        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
//        logger.error(ex.getMessage());
//        logger.error(">>>>>>>>>>>>> Error not handled here so , Please contact operator >>>>>>>> ");
//        final ApiError apiError = new ApiError(ResponseStatus.FAIL, httpStatus.value(), ErrorMessages.databaseError(), ex.getClass().getName());
//        return new ResponseEntity<Object>(apiError, new HttpHeaders(), httpStatus);
//    }
