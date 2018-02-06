package ie.mid.model;

/**
 * Created by Cillian on 06/02/2018.
 */

public class CreatedSubmission {
    private String id;
    private String submissionId;
    private String cardId;
    private String status;
    private String createdDate;
    private String updatedDate;

    public CreatedSubmission() {
    }

    public CreatedSubmission(String id, String submissionId, String cardId, String status, String createdDate, String updatedDate) {
        this.id = id;
        this.submissionId = submissionId;
        this.cardId = cardId;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public CreatedSubmission(String id, String submissionId, String cardId, String status) {
        this.id = id;
        this.submissionId = submissionId;
        this.cardId = cardId;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
