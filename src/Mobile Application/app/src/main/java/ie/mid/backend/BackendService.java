package ie.mid.backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import ie.mid.model.HttpCall;

import static android.content.ContentValues.TAG;

class BackendService {

    private static String ENDPOINT = "http://10.0.2.2:8080"; //LOCALHOST URL FOR VM
    private String endpointExtention;
    private final String LOG_TAG = "BACKEND_SERVICE";

    BackendService(Context context, String endpointExtention) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        ENDPOINT = prefs.getString("key_server_address", null);
        this.endpointExtention = endpointExtention;
    }

    String sendGet(HttpCall call) {
        return executeCall("GET",call);
    }

    String sendPost(HttpCall call) {
        return executeCall("POST",call);
    }

    String sendPut(HttpCall call) {
        return executeCall("PUT",call);
    }

    String sendDelete(HttpCall call) {
        return executeCall("DELETE",call);
    }

    private String getEndpointExtention() {
        return endpointExtention;
    }

    void setEndpointExtention(String endpointExtention) {
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
            Log.e(TAG, "Error: " + e.toString());
        }
        return null;
    }

    private String sendGet(String url, HttpCall call) throws  IOException{
        Log.i(LOG_TAG,"Sending 'GET' request to URL : " + url);
        URL obj = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestMethod("GET");
        if(call.getAuthHeader() != null)
            urlConnection.setRequestProperty ("Authorization", call.getAuthHeader());
        urlConnection.setRequestProperty("Content-Type", "application/json");
        return evaluateUrlConnection(urlConnection);
    }

    private String sendPost(String url, HttpCall call) throws IOException {
        Log.i(LOG_TAG,"Sending 'POST' request to URL : " +url);
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
        Log.i(LOG_TAG,"Sending 'PUT' request to URL : " + url);
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
        Log.i(LOG_TAG,"Sending 'DELETE' request to URL : " + url);
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

    private String evaluateUrlConnection(HttpURLConnection connection) throws IOException{
        int responseCode = connection.getResponseCode();
        Log.i(LOG_TAG,"Response Code : " + responseCode);
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

}
