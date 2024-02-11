package fyp.canteen.fypcore.enums;

public enum FoodType {
    MEAL("Meal"), DRINKS("Drinks"), MISC("Misc");

    private String text;

     FoodType(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
