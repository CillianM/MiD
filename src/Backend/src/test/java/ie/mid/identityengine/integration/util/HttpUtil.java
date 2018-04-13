package ie.mid.identityengine.integration.util;

import ie.mid.identityengine.integration.model.HttpCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    private static String ENDPOINT = "http://localhost:8080/mid";
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String JSON = "application/json";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    public static String endpointExtention;
    public static String lastResponse;
    public static int lastReponseCode;


    HttpUtil() {
    }

    public static String sendGet(HttpCall call) {
        return executeCall("GET", call);
    }

    public static String sendPost(HttpCall call) {
        return executeCall("POST", call);
    }

    public static String sendPut(HttpCall call) {
        return executeCall("PUT", call);
    }

    public static String sendDelete(HttpCall call) {
        return executeCall("DELETE", call);
    }

    private static String getEndpointExtention() {
        return endpointExtention;
    }

    public void setEndpointExtention(String endpointExtention) {
        this.endpointExtention = endpointExtention;
    }


    private static String executeCall(String call, HttpCall httpCall) {
        String url = ENDPOINT + getEndpointExtention();
        try {
            switch (call) {
                case "GET":
                    return sendGet(url, httpCall);
                case "POST":
                    return sendPost(url, httpCall);
                case "PUT":
                    return sendPut(url, httpCall);
                case "DELETE":
                    return sendDelete(url, httpCall);
            }

        } catch (Exception e) {
            logger.error("Error: " + e.toString());
        }
        return null;
    }

    private static String evaluateUrlConnection(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        logger.info("Response Code : " + responseCode);
        lastReponseCode = responseCode;
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            lastResponse = response.toString();
            return response.toString();
        } else return null;
    }

    private static String sendGet(String url, HttpCall call) throws IOException {
        logger.info("Sending 'GET' request to URL : " + url);
        URL obj = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestMethod("GET");
        if (call.getAuthHeader() != null)
            urlConnection.setRequestProperty("Authorization", call.getAuthHeader());
        urlConnection.setRequestProperty("Content-Type", "application/json");
        return evaluateUrlConnection(urlConnection);
    }

    private static String sendPost(String url, HttpCall call) throws IOException {
        logger.info("Sending 'POST' request to URL : " + url);
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setUseCaches(false);
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");
        if (call.getAuthHeader() != null)
            urlConnection.setRequestProperty("Authorization", call.getAuthHeader());
        urlConnection.setRequestProperty("Content-Type", "application/json");
        OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
        if (call.getJsonBody() != null)
            wr.write(call.getJsonBody());
        wr.flush();
        wr.close();
        return evaluateUrlConnection(urlConnection);
    }

    private static String sendPut(String url, HttpCall call) throws IOException {
        logger.info("Sending 'PUT' request to URL : " + url);
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("PUT");
        if (call.getAuthHeader() != null)
            urlConnection.setRequestProperty("Authorization", call.getAuthHeader());
        urlConnection.setRequestProperty("Content-Type", "application/json");
        OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
        if (call.getJsonBody() != null)
            out.write(call.getJsonBody());
        out.flush();
        out.close();
        return evaluateUrlConnection(urlConnection);
    }

    private static String sendDelete(String url, HttpCall call) throws IOException {
        logger.info("Sending 'DELETE' request to URL : " + url);
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("DELETE");
        if (call.getAuthHeader() != null)
            urlConnection.setRequestProperty("Authorization", call.getAuthHeader());
        urlConnection.setRequestProperty("Content-Type", "application/json");
        OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
        out.flush();
        out.close();
        return evaluateUrlConnection(urlConnection);
    }

}