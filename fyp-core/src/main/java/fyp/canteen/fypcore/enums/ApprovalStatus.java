package fyp.canteen.fypcore.enums;

public enum ApprovalStatus {
    DELIVERED("Delivered"), REJECTED("Rejected"), PENDING("Pending");

    private String text;

     ApprovalStatus(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }
}
