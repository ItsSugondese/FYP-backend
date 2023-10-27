package fyp.canteen.fypcore.constants;

public class ErrorMessages {
    public static String alreadyExistsError(String module){
        return module + " already exists.";
    }

    public static String checkConstraintsError(String module){
        return module + " check constraint failed.";
    }

    public static String doesNotExistsError(String module){
        return module + " doesn't exists";
    }

    public static String couldNotDeleteError(String module){
        return "Couldn't delete as it is already used in " + module + ".";
    }

    public static String databaseError(){
        return "Please contact the operator.";
    }

    public static String usedInOtherLocation(){
        return "Already in use by others.";
    }

}
