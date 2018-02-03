package ie.mid.identityengine.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HTTPCaller {

    private HttpClient httpClient = HttpClientBuilder.create().build();

    // HTTP GET request
    public String sendPost(String urlString, JSONObject data) {
        try {
            HttpPost request = new HttpPost(urlString);
            StringEntity params = new StringEntity(data.toString());
            request.addHeader("Content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (response.getEntity().getContent())));

            StringBuilder builder = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                builder.append(output);
            }

            return builder.toString();

        } catch (Exception e) {

            return null;

        }
    }


}
