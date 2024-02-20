package fyp.canteen.fypcore.exception;

public class AppExceptionWithData extends RuntimeException {
    public AppExceptionWithData(String message) {
        super(message);
    }

    public AppExceptionWithData(String message, Object data,  Throwable cause) {
        super(message, cause);
    }
}
