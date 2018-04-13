package ie.mid.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import ie.mid.util.HashUtil;

public class SubmissionData {

    private int identityTypeVersion;
    private String imageData;
    private List<SubmissionField> submissionFields;

    public SubmissionData(){}

    public SubmissionData(int identityTypeVersion, String imageData, List<CardField> cardFields) {
        this.identityTypeVersion = identityTypeVersion;
        this.imageData = imageData;
        this.submissionFields = getSubmissionFieldsFromCardFields(cardFields);
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

    public void setSubmissionFields(List<SubmissionField> cardFields) {
        this.submissionFields = cardFields;
    }

    public int getIdentityTypeVersion() {
        return identityTypeVersion;
    }

    public void setIdentityTypeVersion(int identityTypeVersion) {
        this.identityTypeVersion = identityTypeVersion;
    }

    public String createSubmissionHash(){
        StringBuilder builder = new StringBuilder();
        for(SubmissionField submissionField : submissionFields){
            String submissionEntry = submissionField.getFieldType() + ":" + submissionField.getFieldEntry();
            String b64SubmissionEntry = HashUtil.hashString(submissionEntry) + ",";
            builder.append(b64SubmissionEntry);
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString().replace("\n","");
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

    public List<SubmissionField> getSubmissionFieldsFromCardFields(List<CardField> cardFields) {
        List<SubmissionField> submissionFields = new ArrayList<>();
        for(CardField cardField: cardFields){
            submissionFields.add(new SubmissionField(cardField.getFieldEntry(),cardField.getFieldType(),cardField.getFieldTitle()));
        }
        return submissionFields;
    }

    public static SubmissionData getSubmissionData(String submissionData){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(submissionData,SubmissionData.class);
        } catch (IOException e) {
            return null;
        }
    }
}
