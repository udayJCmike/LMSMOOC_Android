package com.deemsys.lmsmooc;


 
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
 
import com.google.android.gcm.GCMRegistrar; 
 
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import android.util.Log;
 
public class Controller extends Application{
     
    private  final int MAX_ATTEMPTS = 5;
    private  final int BACKOFF_MILLI_SECONDS = 2000;
    private  final Random random = new Random();
     
     
     
    void register(final Context context,final String regId,String deviceimei) {
          
        Log.i(Config.TAG, "registering device (regId = " + regId + ")");
         
        String serverUrl =Config.ServerUrl + "Login.php?service=insertofgcm";
        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
  
        params1.add(new BasicNameValuePair("regId", regId));
        params1.add(new BasicNameValuePair("imei", deviceimei));
       
         
        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
      
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
             
            Log.d(Config.TAG, "Attempt #" + i + " to register");
             
            try {
               
                displayMessageOnScreen(context, "sfsdf",context.getString(
                        R.string.server_registering, i, MAX_ATTEMPTS));
                 
             
                JsonParser jLogin = new JsonParser();
    			
    			JSONObject json = jLogin
    					.makeHttpRequest(serverUrl, "POST", params1);
    			System.out.println(json);
                GCMRegistrar.setRegisteredOnServer(context, true);
                 
               
                String message = context.getString(R.string.server_registered);
                displayMessageOnScreen(context," dsafsd",message);
                 
                return;
            } catch (Exception e) {
                 
               
                 
                Log.e(Config.TAG, "Failed to register on attempt " + i + ":" + e);
                 
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                     
                    Log.d(Config.TAG, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                     
                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                    Log.d(Config.TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return;
                }
                 
               
                backoff *= 2;
            }
        }
         
        String message = context.getString(R.string.server_register_error,
                MAX_ATTEMPTS);
         
        
        displayMessageOnScreen(context, "sdaf",message);
    }
 
   
     void unregister(final Context context, final String regId) {
          
        Log.i(Config.TAG, "unregistering device (regId = " + regId + ")");
         
        String serverUrl = Config.YOUR_SERVER_URL + "/unregister";
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
         
        try {
        	
            post(serverUrl, params);
            GCMRegistrar.setRegisteredOnServer(context, false);
            String message = context.getString(R.string.server_unregistered);
            displayMessageOnScreen(context,"sdfas", message);
        } catch (IOException e) {
             
          
            String message = context.getString(R.string.server_unregister_error,
                    e.getMessage());
            displayMessageOnScreen(context,"sadf", message);
        }
    }
 
    // Issue a POST request to the server.
    private static void post(String endpoint, Map<String, String> params)
            throws IOException {    
         
        URL url;
        try {
             
            url = new URL(endpoint);
             
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
         
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
         
        
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
         
        String body = bodyBuilder.toString();
         
        Log.v(Config.TAG, "Posting '" + body + "' to " + url);
       
        byte[] bytes = body.getBytes();
         
        HttpURLConnection conn = null;
        try {
             
            Log.e("URL", "> " + url);
             
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
             
            // handle the response
            int status = conn.getResponseCode();
             
            // If response is not success
            if (status != 200) {
                 
              throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
      }
     
     
     
    // Checking for all possible internet providers
    public boolean isConnectingToInternet(){
         
        ConnectivityManager connectivity = 
                             (ConnectivityManager) getSystemService(
                              Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
  
          }
          return false;
    }
     
   // Notifies UI to display a message.
   void displayMessageOnScreen(Context context, String title,String message) {
          
        Intent intent = new Intent(Config.DISPLAY_MESSAGE_ACTION);
        intent.putExtra(Config.EXTRA_MESSAGE, message);
        intent.putExtra("name", "thendral");
 
 
        // Send Broadcast to Broadcast receiver with message
        context.sendBroadcast(intent);
         
    }
     
     
   //Function to display simple Alert Dialog
   @SuppressWarnings("deprecation")
public void showAlertDialog(Context context, String title, String message,
            Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
 
        // Set Dialog Title
        alertDialog.setTitle(title);
 
        // Set Dialog Message
        alertDialog.setMessage(message);
 
        if(status != null)
            // Set alert dialog icon
            //alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
 
        // Set OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                 
            }
        });
 
        // Show Alert Message
        alertDialog.show();
    }
     
    private PowerManager.WakeLock wakeLock;
     
    @SuppressWarnings("deprecation")
	@SuppressLint("Wakelock")
	public  void acquireWakeLock(Context context) {
        if (wakeLock != null) wakeLock.release();
 
        PowerManager pm = (PowerManager) 
                          context.getSystemService(Context.POWER_SERVICE);
         
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "WakeLock");
         
        wakeLock.acquire();
    }
 
    public  void releaseWakeLock() {
        if (wakeLock != null) wakeLock.release(); wakeLock = null;
    }
    
}