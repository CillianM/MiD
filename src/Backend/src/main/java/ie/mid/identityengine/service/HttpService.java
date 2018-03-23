package ie.mid.identityengine.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpService {

    private String ENDPOINT = "http://localhost:3000/api";
    private static final Logger logger = LogManager.getLogger(HttpService.class);
    private String endpointExtention;
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String JSON = "application/json";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";


    HttpService() {
    }

    String sendGet() {
        String[] params = new String[]{GET};
        return executeCall(params);
    }

    String sendPost(String json) {
        String[] params = new String[]{POST, json};
        return executeCall(params);
    }

    String sendPut(String json) {
        String[] params = new String[]{PUT, json};
        return executeCall(params);
    }

    String sendDelete() {
        String[] params = new String[]{DELETE};
        return executeCall(params);
    }

    private String getEndpointExtention() {
        return endpointExtention;
    }

    void setEndpointExtention(String endpointExtention) {
        this.endpointExtention = endpointExtention;
    }


    private String executeCall(String [] args){
        try {
            if (GET.equals(args[0])) {
                return sendGet(ENDPOINT + getEndpointExtention());
            } else if (POST.equals(args[0])) {
                return sendPost(ENDPOINT + getEndpointExtention(), args[1]);
            } else if (PUT.equals(args[0])) {
                return sendPut(ENDPOINT + getEndpointExtention(), args[1]);
            } else if (DELETE.equals(args[0])) {
                return sendDelete(ENDPOINT + getEndpointExtention());
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

    private String sendGet(String url) throws  IOException{
        logger.debug("Sending 'GET' request to URL : " + url);
        URL obj = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestMethod(GET);
        urlConnection.setRequestProperty(CONTENT_TYPE, JSON);
        return evaluateUrlConnection(urlConnection);
    }

    private String sendPost(String url, String json) throws IOException {
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
        return evaluateUrlConnection(urlConnection);
    }

    private String sendPut(String url, String json) throws IOException {
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
        return evaluateUrlConnection(urlConnection);
    }

    private String sendDelete(String url) throws IOException {
        logger.debug("Sending 'DELETE' request to URL : " + url);
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod(DELETE);
        urlConnection.setRequestProperty(CONTENT_TYPE, JSON);
        OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
        out.flush();
        out.close();
        return evaluateUrlConnection(urlConnection);
    }

}
