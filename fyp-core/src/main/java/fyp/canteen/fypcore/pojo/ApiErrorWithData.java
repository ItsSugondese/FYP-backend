package fyp.canteen.fypcore.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import fyp.canteen.fypcore.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorWithData {

    private ResponseStatus status;
    private int httpCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("errors")
    private List<String> errors;
    private Object data;




    public ApiErrorWithData(ResponseStatus status, final int httpCode, final String message, final String error, Object data) {
        super();
        this.status = status;
        this.httpCode = httpCode;
        this.message = message;
        this.data = data;
        errors = Arrays.asList(error);
    }


}


