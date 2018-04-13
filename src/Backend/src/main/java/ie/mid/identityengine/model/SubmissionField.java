package ie.mid.identityengine.model;

public class SubmissionField {
    private String fieldEntry;
    private String fieldType;
    private String fieldTitle;

    public SubmissionField() {
    }

    public SubmissionField(String fieldEntry, String fieldType) {
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
}
