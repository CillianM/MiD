package ie.mid.backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

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

    String sendGet() {
        String[] params = new String[]{"GET"};
        return executeCall(params);
    }

    String sendPost(String json) {
        String[] params = new String[]{"POST", json};
        return executeCall(params);
    }

    String sendPut(String json) {
        String[] params = new String[]{"PUT", json};
        return executeCall(params);
    }

    String sendDelete() {
        String[] params = new String[]{"DELETE"};
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
            switch (args[0]) {
                case "GET":
                    return sendGet(ENDPOINT + getEndpointExtention());
                case "POST":
                    return sendPost(ENDPOINT + getEndpointExtention(), args[1]);
                case "PUT":
                    return sendPut(ENDPOINT + getEndpointExtention(), args[1]);
                case "DELETE":
                    return sendDelete(ENDPOINT + getEndpointExtention());
            }

        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.toString());
        }
        return null;
    }

    private String sendGet(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");

        int responseCode = con.getResponseCode();
        Log.i(LOG_TAG,"Sending 'GET' request to URL : " + url);
        Log.i(LOG_TAG,"Response Code : " + responseCode);

        if(responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
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

    private String sendPost(String url, String json) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        if (json != null)
            wr.write(json);
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        Log.i(LOG_TAG,"Sending 'POST' request to URL : " + url);
        Log.i(LOG_TAG,"Response Code : " + responseCode);
        if(responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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

    private String sendPut(String url, String json) throws Exception {
        HttpURLConnection httpCon = (HttpURLConnection) new URL(url).openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("PUT");
        httpCon.setRequestProperty("Content-Type", "application/json");
        OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
        if (json != null)
            out.write(json);
        out.flush();
        out.close();

        int responseCode = httpCon.getResponseCode();
        Log.i(LOG_TAG,"Sending 'PUT' request to URL : " + url);
        Log.i(LOG_TAG,"Response Code : " + responseCode);
        if(responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
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

    private String sendDelete(String url) throws Exception {
        HttpURLConnection httpCon = (HttpURLConnection) new URL(url).openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("DELETE");
        httpCon.setRequestProperty("Content-Type", "application/json");
        OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
        out.flush();
        out.close();

        int responseCode = httpCon.getResponseCode();
        Log.i(LOG_TAG,"Sending 'DELETE' request to URL : " + url);
        Log.i(LOG_TAG,"Response Code : " + responseCode);
        if(responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
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
