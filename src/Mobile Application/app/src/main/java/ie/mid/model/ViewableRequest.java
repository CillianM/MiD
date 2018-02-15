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
        this.setSender(request.getSender());
        this.setStatus(request.getStatus());
        this.setRecipient(request.getRecipient());
        this.senderReceiverName = senderReceiverName;
    }

    public String getSenderReceiverName() {
        return senderReceiverName;
    }

    public void setSenderReceiverName(String senderReceiverName) {
        this.senderReceiverName = senderReceiverName;
    }
}
