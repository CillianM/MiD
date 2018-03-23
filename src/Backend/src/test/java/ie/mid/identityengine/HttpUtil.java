package ie.mid.identityengine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpUtil {

    private static String ENDPOINT = "http://localhost:8080/mid";
    private static final Logger logger = LogManager.getLogger(HttpUtil.class);
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String JSON = "application/json";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    public static String endpointExtention;
    public static String lastResponse;
    public static int latestResponseCode;


    HttpUtil() {
    }

    static void sendGet() {
        String[] params = new String[]{GET};
        executeCall(params);
    }

    static void sendPost(String json) {
        String[] params = new String[]{POST, json};
        executeCall(params);
    }

    static void sendPut(String json) {
        String[] params = new String[]{PUT, json};
        executeCall(params);
    }

    static void sendDelete() {
        String[] params = new String[]{DELETE};
        executeCall(params);
    }

    private static void executeCall(String[] args) {
        try {
            if (GET.equals(args[0])) {
                sendGet(ENDPOINT + endpointExtention);
            } else if (POST.equals(args[0])) {
                sendPost(ENDPOINT + endpointExtention, args[1]);
            } else if (PUT.equals(args[0])) {
                sendPut(ENDPOINT + endpointExtention, args[1]);
            } else if (DELETE.equals(args[0])) {
                sendDelete(ENDPOINT + endpointExtention);
            }
        } catch (Exception e) {
            logger.error("Error: " + e.toString());
        }
    }

    private static void evaluateUrlConnection(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        logger.info("Response Code : " + responseCode);
        latestResponseCode = responseCode;
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            lastResponse = response.toString();
        } else lastResponse = null;
    }

    private static void sendGet(String url) throws IOException {
        logger.debug("Sending 'GET' request to URL : " + url);
        URL obj = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestMethod(GET);
        urlConnection.setRequestProperty(CONTENT_TYPE, JSON);
        evaluateUrlConnection(urlConnection);
    }

    private static void sendPost(String url, String json) throws IOException {
        logger.debug("Sending 'POST' request to URL : " + url);
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setUseCaches(false);
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod(POST);
        urlConnection.setRequestProperty(CONTENT_TYPE, JSON);
        OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
        if (json != null)
            wr.write(json);
        wr.flush();
        wr.close();
        evaluateUrlConnection(urlConnection);
    }

    private static void sendPut(String url, String json) throws IOException {
        logger.debug("Sending 'PUT' request to URL : " + url);
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod(PUT);
        urlConnection.setRequestProperty(CONTENT_TYPE, JSON);
        OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
        if (json != null)
            out.write(json);
        out.flush();
        out.close();
        evaluateUrlConnection(urlConnection);
    }

    private static void sendDelete(String url) throws IOException {
        logger.debug("Sending 'DELETE' request to URL : " + url);
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod(DELETE);
        urlConnection.setRequestProperty(CONTENT_TYPE, JSON);
        OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
        out.flush();
        out.close();
        evaluateUrlConnection(urlConnection);
    }

}