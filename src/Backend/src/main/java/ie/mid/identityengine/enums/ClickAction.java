package ie.mid.identityengine.enums;

public enum ClickAction {
    OPEN_REQUEST("OPEN_REQUEST"),
    OPEN_SUBMISSION("OPEN_SUBMISSION");

    private String clickAction;

    ClickAction(String clickAction) {
        this.clickAction = clickAction;
    }

    @Override
    public String toString() {
        return clickAction;
    }
}
