package ie.mid.model;

/**
 * Created by Cillian on 06/02/2018.
 */

public class CreatedSubmission {
    private String id;
    private String submissionId;
    private String cardId;
    private String data;
    private String createdDate;
    private String updatedDate;
    private String submissionKey;

    public CreatedSubmission() {
    }

    public CreatedSubmission(String id, String submissionId, String cardId, String data, String submissionKey,String createdDate, String updatedDate) {
        this.id = id;
        this.submissionId = submissionId;
        this.cardId = cardId;
        this.data = data;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.submissionKey = submissionKey;
    }

    public CreatedSubmission(String id, String submissionId, String cardId, String data) {
        this.id = id;
        this.submissionId = submissionId;
        this.cardId = cardId;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getSubmissionKey() {
        return submissionKey;
    }

    public void setSubmissionKey(String submissionKey) {
        this.submissionKey = submissionKey;
    }
}
