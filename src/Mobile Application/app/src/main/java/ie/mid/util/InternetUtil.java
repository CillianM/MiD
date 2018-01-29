package ie.mid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Cillian on 28/01/2018.
 */

public class InternetUtil {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private static boolean isServerLive(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String address = prefs.getString("key_server_address", null);
        if (address != null) {
            String host = address.substring(0, address.lastIndexOf(":"));
            if (host.equals("http://10.0.2.2")) return true;

            try (Socket socket = new Socket()) {
                Runtime runtime = Runtime.getRuntime();
                Process proc = runtime.exec("ping -c 1 " + host);
                int mPingResult = proc.waitFor();
                return mPingResult == 0;
            } catch (IOException | InterruptedException e) {
                return false;
            }
        }
        return false;
    }

    private class ServerChecker implements Runnable {

        @Override
        public void run() {

        }
    }
}
