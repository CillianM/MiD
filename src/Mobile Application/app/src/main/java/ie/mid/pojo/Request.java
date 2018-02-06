package ie.mid.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

public class Request {

    private String id;
    private String sender;
    private String recipient;
    private String status;
    private String indentityTypeId;
    private String identityTypeFields;
    private String identityTypeValues;

    public Request() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIndentityTypeId() {
        return indentityTypeId;
    }

    public void setIndentityTypeId(String indentityTypeId) {
        this.indentityTypeId = indentityTypeId;
    }

    public String getIdentityTypeFields() {
        return identityTypeFields;
    }

    public void setIdentityTypeFields(String identityTypeFields) {
        this.identityTypeFields = identityTypeFields;
    }

    public String getIdentityTypeValues() {
        return identityTypeValues;
    }

    public void setIdentityTypeValues(String identityTypeValues) {
        this.identityTypeValues = identityTypeValues;
    }

    public String toJsonString() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (IOException e) {
            return null;
        }

    }
}
