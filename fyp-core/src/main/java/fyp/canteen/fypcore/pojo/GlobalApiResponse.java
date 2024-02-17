package fyp.canteen.fypcore.pojo;
import fyp.canteen.fypcore.enums.CRUD;
import fyp.canteen.fypcore.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlobalApiResponse implements Serializable {
    private ResponseStatus status;
    private String message;
    private Object data;
    private CRUD crud;

    public void setResponse(String message, ResponseStatus status, Object data, CRUD crud) {
        this.message = message;
        this.status = status;
        this.data = data;
        this.crud = crud;
    }

}
