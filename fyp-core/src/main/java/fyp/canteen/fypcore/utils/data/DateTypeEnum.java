package fyp.canteen.fypcore.utils.data;

public enum DateTypeEnum {
    DAY(1),
    WEEK(7),
    MONTH(30),
    YEAR(365);

    private Integer noOfDays;
    DateTypeEnum(Integer noOfDays){
        this.noOfDays = noOfDays;
    }

    public Integer getNoOfDays() {
        return noOfDays;
    }
}

