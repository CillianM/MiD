package ie.mid.identityengine.integration.model;

import org.apache.tomcat.util.codec.binary.Base64;

public class HttpCall {

    private String authHeader;
    private String jsonBody;

    public HttpCall() {
    }

    public HttpCall(String jsonBody) {
        this.jsonBody = jsonBody;
    }

    public HttpCall(String username, String password) {
        this.authHeader = createAuthHeader(username, password);
    }

    public HttpCall(String jsonBody, String username, String password) {
        this.jsonBody = jsonBody;
        this.authHeader = createAuthHeader(username, password);
    }

    public String getAuthHeader() {
        return authHeader;
    }

    private String createAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        return "Basic " + new String(Base64.encodeBase64String(auth.getBytes())).replace("\n", "");
    }

    public String getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(String jsonBody) {
        this.jsonBody = jsonBody;
    }
}
