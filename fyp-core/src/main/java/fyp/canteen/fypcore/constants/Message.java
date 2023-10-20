package fyp.canteen.fypcore.constants;

import lombok.*;


public class Message {

    public static String Crud(String operation, String module){
        return module + " has been " + operation + " successfully.";
    }

    public static String IdNotFound(String module){
        return module + " with that Id doesn't exist.";
    }

}
