package ie.mid.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.util.List;

/**
 * Created by Cillian on 02/02/2018.
 */

public class SubmissionData {

    private int versionNumber;
    private String imageData;
    private List<CardField> cardFields;

    public SubmissionData(){}

    public SubmissionData(int versionNumber, String imageData, List<CardField> cardFields) {
        this.versionNumber = versionNumber;
        this.imageData = imageData;
        this.cardFields = cardFields;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public List<CardField> getCardFields() {
        return cardFields;
    }

    public void setCardFields(List<CardField> cardFields) {
        this.cardFields = cardFields;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
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