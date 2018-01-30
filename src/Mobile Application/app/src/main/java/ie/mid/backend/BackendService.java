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

    private static String ENDPOINT = "http://10.0.2.2:8080"; //LOCALHOST URL FOR VM
    private String endpointExtention;

    public BackendService(Context context, String endpointExtention) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        ENDPOINT = prefs.getString("key_server_address", null);
        this.endpointExtention = endpointExtention;
    }

    public String sendGet() {
        String[] params = new String[]{"GET"};
        try {
            return new BackendCaller().execute(params).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sendPost(String json) {
        String[] params = new String[]{"POST", json};
        try {
            return new BackendCaller().execute(params).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sendPut(String json) {
        String[] params = new String[]{"PUT", json};
        try {
            return new BackendCaller().execute(params).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sendDelete() {
        String[] params = new String[]{"DELETE"};
        try {
            return new BackendCaller().execute(params).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getEndpointExtention() {
        return endpointExtention;
    }

    void setEndpointExtention(String endpointExtention) {
        this.endpointExtention = endpointExtention;
    }

    private class BackendCaller extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                switch (strings[0]) {
                    case "GET":
                        return sendGet(ENDPOINT + getEndpointExtention());
                    case "POST":
                        return sendPost(ENDPOINT + getEndpointExtention(), strings[1]);
                    case "PUT":
                        return sendPut(ENDPOINT + getEndpointExtention(), strings[1]);
                    case "DELETE":
                        return sendDelete(ENDPOINT + getEndpointExtention());
                }

            } catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }

        protected void onPostExecute(String feed) {

        }

        private String sendGet(String url) throws Exception {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
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
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
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
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
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
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
    }
}
