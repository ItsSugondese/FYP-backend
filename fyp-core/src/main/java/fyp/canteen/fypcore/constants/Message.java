package fyp.canteen.fypcore.constants;


public class Message {

    public static String crud(String operation, String module){
        return module + " has been " + operation + " successfully.";
    }

    public static String idNotFound(String module){
        return module + " with that Id doesn't exist.";
    }


}
