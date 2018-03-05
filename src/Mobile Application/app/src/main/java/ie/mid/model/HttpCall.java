package ie.mid.model;

import android.util.Base64;

/**
 * Created by Cillian on 05/03/2018.
 */

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
        this.authHeader = "Basic " + new String(Base64.encode(auth.getBytes(),Base64.DEFAULT)).replace("\n","");
    }

    public String getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(String jsonBody) {
        this.jsonBody = jsonBody;
    }
}
