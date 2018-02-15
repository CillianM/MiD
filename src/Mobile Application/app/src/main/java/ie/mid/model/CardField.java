package ie.mid.model;

/**
 * Created by Cillian on 22/10/2017.
 */

public class CardField {
    private String fieldEntry;
    private String fieldType;
    private String fieldTitle;
    private int dataIcon;

    public CardField(){}

    public CardField(String fieldEntry, String fieldType) {
        this.fieldEntry = fieldEntry;
        this.fieldType = fieldType;
    }

    public String getFieldEntry() {
        return fieldEntry;
    }

    public void setFieldEntry(String fieldEntry) {
        this.fieldEntry = fieldEntry;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldTitle() {
        return fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public int getDataIcon() {
        return dataIcon;
    }

    public void setDataIcon(int dataIcon) {
        this.dataIcon = dataIcon;
    }
}
