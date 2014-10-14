package com.deemsys.lmsmooc;

import com.google.android.gcm.GCMRegistrar;
import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class SplashActivity extends Activity {
	Controller aController;
	 public static String deviceIMEI;
	private static int SPLASH_TIME_OUT = 3000;
	AsyncTask<Void, Void, Void> mRegisterTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		ActionBar actions = getActionBar();
		actions.hide();
		  aController = (Controller) getApplicationContext();
          
          
          // Check if Internet present
          if (!aController.isConnectingToInternet()) {
               
              // Internet Connection is not present
              aController.showAlertDialog(SplashActivity.this,
                      "Internet Connection Error",
                      "Please connect to Internet connection", false);
              // stop executing code by return
              return;
          }
           deviceIMEI = "";
//          if(Config.SECOND_SIMULATOR){
//               
//              //Make it true in CONFIG if you want to open second simutor
//              // for testing actually we are using IMEI number to save a unique device
//               
//              deviceIMEI = "000000000000001";
//          }   
//          else
//          {
            // GET IMEI NUMBER      
           TelephonyManager tManager = (TelephonyManager) getBaseContext()
              .getSystemService(Context.TELEPHONY_SERVICE);
            deviceIMEI = tManager.getDeviceId(); 
         // }
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);

		if (regId.equals("")) {
			Log.i("GCM K", "--- Regid = ''" + regId);
			// Register with GCM
			GCMRegistrar.register(this, Config.GOOGLE_SENDER_ID);

		} else {

			// Device is already registered on GCM Server
			if (GCMRegistrar.isRegisteredOnServer(this)) {
			//	final Context context = this;
				// Skips registration.
//				Toast.makeText(getApplicationContext(),
//						"Already registered with GCM Server", Toast.LENGTH_LONG)
					//	.show();
				Log.i("GCM K", "Already registered with GCM Server");

				// GCMRegistrar.unregister(context);

			} else {

				Log.i("GCM K", "-- gO for registration--");

				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;

				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {

						// Register on our server
						// On server creates a new user
						aController.register(context,  regId,deviceIMEI);

						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;

						finish();
					}

				};

				// execute AsyncTask
				mRegisterTask.execute(null, null, null);
			}
		}

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				Intent i = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(i);

				finish();
			}
		}, SPLASH_TIME_OUT);
	}
	 private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
         
         @Override
         public void onReceive(Context context, Intent intent) {
              
             String newMessage = intent.getExtras().getString(Config.EXTRA_MESSAGE);
              
             // Waking up mobile if it is sleeping
             aController.acquireWakeLock(getApplicationContext());
              
             // Display message on the screen
//             lblMessage.append(newMessage + "");         
              
             Toast.makeText(getApplicationContext(), 
                     "Got Message: " + newMessage, 
                     Toast.LENGTH_LONG).show();
              
             // Releasing wake lock
             aController.releaseWakeLock();
         }
     };
      
     @Override
     protected void onDestroy() {
         // Cancel AsyncTask
         if (mRegisterTask != null) {
             mRegisterTask.cancel(true);
         }
         try {
             // Unregister Broadcast Receiver
             unregisterReceiver(mHandleMessageReceiver);
              
             //Clear internal resources.
             GCMRegistrar.onDestroy(this);
              
         } catch (Exception e) {
             Log.e("UnRegister Receiver Error", "> " + e.getMessage());
         }
         super.onDestroy();
     }
  
}
