package ie.mid.model;

import ie.mid.pojo.Request;

/**
 * Created by Cillian on 14/02/2018.
 */

public class ViewableRequest extends Request {
    private String senderReceiverName;

    public ViewableRequest() {
    }

    public ViewableRequest(Request request, String senderReceiverName) {
        this.setId(request.getId());
        this.setIdentityTypeFields(request.getIdentityTypeFields());
        this.setIdentityTypeValues(request.getIdentityTypeValues());
        this.setIndentityTypeId(request.getIndentityTypeId());
        this.setSenderId(request.getSenderId());
        this.setStatus(request.getStatus());
        this.setRecipientId(request.getRecipientId());
        this.setCreatedAt(request.getCreatedAt());
        this.setCertificateId(request.getCertificateId());
        this.senderReceiverName = senderReceiverName;
    }

    public String getSenderReceiverName() {
        return senderReceiverName;
    }

    public void setSenderReceiverName(String senderReceiverName) {
        this.senderReceiverName = senderReceiverName;
    }
}
