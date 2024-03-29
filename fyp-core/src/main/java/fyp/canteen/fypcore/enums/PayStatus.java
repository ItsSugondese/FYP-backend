package fyp.canteen.fypcore.enums;

import lombok.Getter;

@Getter
public enum PayStatus {
    PAID("Paid"), PARTIAL_PAID("Partial Paid"), UNPAID("Unpaid");
    final String text;
    PayStatus(String text){
        this.text = text;
    }
}
