package ie.mid.identityengine.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubmissionData {
    private String imageData;
    private List<SubmissionField> submissionFields;

    public SubmissionData() {
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public List<SubmissionField> getSubmissionFields() {
        return submissionFields;
    }

    public void setSubmissionFields(List<SubmissionField> submissionFields) {
        this.submissionFields = submissionFields;
    }

    public List<String> getCardFieldEntries() {
        List<String> cardFieldEntries = new ArrayList<>();
        for (SubmissionField submissionField : submissionFields) {
            cardFieldEntries.add(submissionField.getFieldEntry());
        }
        return cardFieldEntries;
    }

    @Override
    public String toString() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (IOException e) {
            return "";
        }

    }
}
