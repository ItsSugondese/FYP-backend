package fyp.canteen.fypcore.enums;

public enum FeedbackStatus {
    POSITIVE("Positive", "text-yellow-500"),  NEUTRAL("Neutral", "bg-[#15803D]"), NEGATIVE("Negative", "text-red-600"),;

    private String text;
    private String style;

    FeedbackStatus(String text, String style){
        this.text = text;
        this.style = style;
    }

    public String getText() {
        return text;
    }
    public String getStyle() {
        return style;
    }
}
