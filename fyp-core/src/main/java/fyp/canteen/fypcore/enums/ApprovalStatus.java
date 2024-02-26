package fyp.canteen.fypcore.enums;

public enum ApprovalStatus {
    DELIVERED("Delivered"), CANCELED("Canceled"), PENDING("Pending");

    private String text;

     ApprovalStatus(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }
}
