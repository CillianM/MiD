package ie.mid.backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class BackendService {

    private static String ENDPOINT = "http://10.0.2.2:8080"; //BACKEND URL GOES HERE
    private String endpointExtention;

    public BackendService(Context context, String endpointExtention) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        ENDPOINT = prefs.getString("key_server_address", null);
        this.endpointExtention = endpointExtention;
    }

    public String sendGet(String json){
        String [] params = new String[]{"GET",json};
        try {
            return new BackendCaller().execute(params).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sendPost(String json){
        String [] params = new String[]{"POST",json};
        try {
            return new BackendCaller().execute(params).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sendPut(String json){
        String [] params = new String[]{"PUT",json};
        try {
            return new BackendCaller().execute(params).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sendDelete(String json){
        String [] params = new String[]{"DELETE",json};
        try {
            return new BackendCaller().execute(params).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getEndpointExtention() {
        return endpointExtention;
    }

    public void setEndpointExtention(String endpointExtention) {
        this.endpointExtention = endpointExtention;
    }

    private class BackendCaller extends AsyncTask<String,Void,String>{

        private String returnedValue;
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(ENDPOINT + getEndpointExtention());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod(strings[0]);
                conn.setRequestProperty("Content-Type", "application/json");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(strings[1]);
                wr.flush();
                wr.close();

                int responseCode = conn.getResponseCode();
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                returnedValue = response.toString();
                return response.toString();
            }
            catch(Exception e)
            {
                System.out.println(e);
            }

            returnedValue = null;
            return null;
        }
        protected void onPostExecute(String feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }
}
