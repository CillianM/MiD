package admin.service;

import admin.model.HttpCall;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HttpService {

    private static String ENDPOINT = "https://mid-secure.ie/mid";
    private static final Logger logger = Logger.getLogger(HttpService.class);
    private String endpointExtention;


    HttpService() {
    }

    public String sendGet(HttpCall call) {
        return executeCall("GET",call);
    }

    public String sendPost(HttpCall call) {
        return executeCall("POST",call);
    }

    public String sendPut(HttpCall call) {
        return executeCall("PUT",call);
    }

    public String sendDelete(HttpCall call) {
        return executeCall("DELETE",call);
    }

    private String getEndpointExtention() {
        return endpointExtention;
    }

    public void setEndpointExtention(String endpointExtention) {
        this.endpointExtention = endpointExtention;
    }


    private String executeCall(String call, HttpCall httpCall){
        String url = ENDPOINT + getEndpointExtention();
        try {
            switch (call) {
                case "GET":
                    return sendGet(url,httpCall);
                case "POST":
                    return sendPost(url,httpCall);
                case "PUT":
                    return sendPut(url,httpCall);
                case "DELETE":
                    return sendDelete(url,httpCall);
            }

        } catch (Exception e) {
            logger.error("Error: " + e.toString());
        }
        return null;
    }

    private String evaluateUrlConnection(HttpURLConnection connection) throws IOException{
        int responseCode = connection.getResponseCode();
        logger.info("Response Code : " + responseCode);
        if(responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
        else return null;
    }

    private String sendGet(String url,HttpCall call) throws  IOException{
        logger.info("Sending 'GET' request to URL : " + url);
        URL obj = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestMethod("GET");
        if(call.getAuthHeader() != null)
            urlConnection.setRequestProperty ("Authorization", call.getAuthHeader());
        urlConnection.setRequestProperty("Content-Type", "application/json");
        return evaluateUrlConnection(urlConnection);
    }

    private String sendPost(String url, HttpCall call) throws IOException {
        logger.info("Sending 'POST' request to URL : " + url);
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setUseCaches(false);
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");
        if(call.getAuthHeader() != null)
            urlConnection.setRequestProperty ("Authorization", call.getAuthHeader());
        urlConnection.setRequestProperty("Content-Type", "application/json");
        OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
        if (call.getJsonBody() != null)
            wr.write(call.getJsonBody());
        wr.flush();
        wr.close();
        return evaluateUrlConnection(urlConnection);
    }

    private String sendPut(String url, HttpCall call) throws IOException {
        logger.info("Sending 'PUT' request to URL : " + url);
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("PUT");
        if(call.getAuthHeader() != null)
            urlConnection.setRequestProperty ("Authorization", call.getAuthHeader());
        urlConnection.setRequestProperty("Content-Type", "application/json");
        OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
        if (call.getJsonBody() != null)
            out.write(call.getJsonBody());
        out.flush();
        out.close();
        return evaluateUrlConnection(urlConnection);
    }

    private String sendDelete(String url, HttpCall call) throws IOException {
        logger.info("Sending 'DELETE' request to URL : " + url);
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("DELETE");
        if(call.getAuthHeader() != null)
            urlConnection.setRequestProperty ("Authorization", call.getAuthHeader());
        urlConnection.setRequestProperty("Content-Type", "application/json");
        OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
        out.flush();
        out.close();
        return evaluateUrlConnection(urlConnection);
    }

}
