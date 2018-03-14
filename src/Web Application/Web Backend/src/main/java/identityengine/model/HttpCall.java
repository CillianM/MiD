package identityengine.model;

import org.apache.tomcat.util.codec.binary.Base64;

public class HttpCall {

    private String authHeader;
    private String jsonBody;

    public HttpCall() {
    }

    public String getAuthHeader() {
        return authHeader;
    }

    public void setAuthHeader(String username,String password) {
        String auth = username + ":" + password;
        this.authHeader = "Basic " + new String(Base64.encodeBase64String(auth.getBytes())).replace("\n","");
    }

    public String getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(String jsonBody) {
        this.jsonBody = jsonBody;
    }
}
