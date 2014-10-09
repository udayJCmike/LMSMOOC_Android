package com.deemsys.lmsmooc;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class AboutTweet extends Activity {
    // Constants
    /**
     * Register your here app https://dev.twitter.com/apps/new and get your
     * consumer key and secret
     * */
	
	static int forcheck=0;
    static String TWITTER_CONSUMER_KEY = "q4WCEk15E9NUPlaEN5OQNdE3c";
    static String TWITTER_CONSUMER_SECRET = "dkyZBFE4KOArbvbveRxJ6YRJR2YlfQBUWlnhKAKlYzD2u7tz27";
 
    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";
 
    static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";
 
    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
 
    // Login button
    Button btnLoginTwitter;
    // Update status button
    Button btnUpdateStatus;
    // Logout button
    Button btnLogoutTwitter;
    // EditText for update
    EditText txtUpdate;
    // lbl update
    TextView lblUpdate;
    TextView lblUserName;
 
    // Progress dialog
    ProgressDialog pDialog;
 
    // Twitter
    private static Twitter twitter;
    private static RequestToken requestToken;
     
    // Shared Preferences
    private static SharedPreferences mSharedPreferences;
     
    // Internet Connection detector
    private ConnectionDetector cd;
     
    // Alert Dialog Manager
   
 
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweetaboutus);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
         
        cd = new ConnectionDetector(getApplicationContext());
 
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
           
            // stop executing code by return
            return;
        }
         
        // Check if twitter keys are set
        if(TWITTER_CONSUMER_KEY.trim().length() == 0 || TWITTER_CONSUMER_SECRET.trim().length() == 0){
            // Internet Connection is not present
           
            return;
        }
 
        // All UI elements
        btnLoginTwitter = (Button) findViewById(R.id.btnLoginTwitter);
        btnUpdateStatus = (Button) findViewById(R.id.btnUpdateStatus);
        btnLogoutTwitter = (Button) findViewById(R.id.btnLogoutTwitter);
        txtUpdate = (EditText) findViewById(R.id.txtUpdateStatus);
        lblUpdate = (TextView) findViewById(R.id.lblUpdate);
        lblUserName = (TextView) findViewById(R.id.lblUserName);
 
        // Shared Preferences
        mSharedPreferences = getApplicationContext().getSharedPreferences(
                "MyPref", 0);
 
        if(forcheck==0)
        {
        	loginToTwitter();
        }
        System.out.println("for check value"+forcheck);
        
        btnLoginTwitter.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                // Call login twitter function
              
            }
        });
 
        /**
         * Button click event to Update Status, will call updateTwitterStatus()
         * function
         * */
        btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // Call update status function
                // Get the status from EditText
                String status = txtUpdate.getText().toString();
 
                // Check for blank text
                if (status.trim().length() > 0) {
                    // update status
                    new updateTwitterStatus().execute(status);
                } else {
                    // EditText is empty
                    Toast.makeText(getApplicationContext(),
                            "Please enter status message", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
 
        /**
         * Button click event for logout from twitter
         * */
        btnLogoutTwitter.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                // Call logout twitter function
                logoutFromTwitter();
            }
        });
 
        /** This if conditions is tested once is
         * redirected from twitter page. Parse the uri to get oAuth
         * Verifier
         * */
        if (!isTwitterLoggedInAlready()) {
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
                // oAuth verifier
                String verifier = uri
                        .getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);
 
                try {
                    // Get the access token
                    AccessToken accessToken = twitter.getOAuthAccessToken(
                            requestToken, verifier);
 
                    // Shared Preferences
                    Editor e = mSharedPreferences.edit();
 
                    // After getting access token, access token secret
                    // store them in application preferences
                    e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
                    e.putString(PREF_KEY_OAUTH_SECRET,
                            accessToken.getTokenSecret());
                    // Store login status - true
                    e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
                    e.commit(); // save changes
 
                    Log.e("Twitter OAuth Token", "> " + accessToken.getToken());
 
                    // Hide login button
                   // btnLoginTwitter.setVisibility(View.GONE);
 
//                    // Show Update Twitter
//                    lblUpdate.setVisibility(View.VISIBLE);
//                    txtUpdate.setVisibility(View.VISIBLE);
//                    btnUpdateStatus.setVisibility(View.VISIBLE);
//                    btnLogoutTwitter.setVisibility(View.VISIBLE);
                    AlertDialog alertDialog = new AlertDialog.Builder(
							AboutTweet.this, R.style.MyFragment).create();

					alertDialog.setTitle("INFO!");

					alertDialog.setMessage("You successfully followed us.");

					alertDialog.setIcon(R.drawable.tick);

					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {

								public void onClick(
										final DialogInterface dialog,
										final int which) {
									try {
						                ConfigurationBuilder builder = new ConfigurationBuilder();
						                builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
						                builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
						                 
						                // Access Token 
						                String access_token = mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, "");
						                // Access Token Secret
						                String access_token_secret = mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, "");
						                 
						                AccessToken accessToken = new AccessToken(access_token, access_token_secret);
						                Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
						                String screenName="imansvijay";
						                System.out.println("in create friendship");
						                     	        twitter.createFriendship(screenName); 
						                     	       Intent intentSignUP = new Intent(getApplicationContext(),
						                     					NewMainActivity.class);
						                     			startActivity(intentSignUP);
						               
						            } catch (TwitterException e) {
						                // Error in updating status
						                Log.d("Twitter Update Error", e.getMessage());
						            }

								}
							});

					// Showing Alert Message
					alertDialog.show(); 
                    // Getting user details from twitter
                    // For now i am getting his name only
                    long userID = accessToken.getUserId();
                    User user = twitter.showUser(userID);
                    String username = user.getName();
                     
                    // Displaying in xml ui
                   // lblUserName.setText(Html.fromHtml("<b>Welcome " + username + "</b>"));
                } catch (Exception e) {
                    // Check log for login errors
                    Log.e("Twitter Login Error", "> " + e.getMessage());
                }
            }
        }
 
    }
 
    /**
     * Function to login twitter
     * */
    private void loginToTwitter() {
        // Check if already logged in
        if (!isTwitterLoggedInAlready()) {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();
             
            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();
 
            try {
                requestToken = twitter
                        .getOAuthRequestToken(TWITTER_CALLBACK_URL);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse(requestToken.getAuthenticationURL()));
              //  Intent intent = new Intent(this, A.class);
                forcheck=1;
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); 
                startActivity(intent);
                
                
                
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        } else {
            // user already logged into twitter
        	new updateTwitterStatus().execute("dfs");
//            Toast.makeText(getApplicationContext(),
//                    "Already Logged into twitter", Toast.LENGTH_LONG).show();
        }
    }
 
    /**
     * Function to update status
     * */
    class updateTwitterStatus extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AboutTweet.this);
            pDialog.setMessage("Updating to twitter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting Places JSON
         * */
        protected String doInBackground(String... args) {
            Log.d("Tweet Text", "> " + args[0]);
            String status = args[0];
           
            	try {
	                ConfigurationBuilder builder = new ConfigurationBuilder();
	                builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
	                builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
	                 
	                // Access Token 
	                String access_token = mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, "");
	                // Access Token Secret
	                String access_token_secret = mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, "");
	                 
	                AccessToken accessToken = new AccessToken(access_token, access_token_secret);
	                Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
	                String screenName="imansvijay";
	                System.out.println("in create friendship");
	                     	        twitter.createFriendship(screenName); 
	               
	            } catch (TwitterException e) {
	                // Error in updating status
	                Log.d("Twitter Update Error", e.getMessage());
	            }

            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog and show
         * the data in UI Always use runOnUiThread(new Runnable()) to update UI
         * from background thread, otherwise you will get error
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            AlertDialog alertDialog = new AlertDialog.Builder(
					AboutTweet.this).create();

			// Setting Dialog Title
			alertDialog.setTitle("INFO!");

			// Setting Dialog Message
			alertDialog.setMessage("You successfully followed us.");

			// Setting Icon to Dialog
			alertDialog.setIcon(R.drawable.tick);

			// Setting OK Button
			alertDialog.setButton("OK",
					new DialogInterface.OnClickListener() {

						public void onClick(final DialogInterface dialog,
								final int which) {
						finish();
						}
					});

			// Showing Alert Message
			alertDialog.show();

           
            // updating UI from Background Thread
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(getApplicationContext(),
//                            "Status tweeted successfully", Toast.LENGTH_SHORT)
//                            .show();
//                    // Clearing EditText field
//                    txtUpdate.setText("");
//                }
//            });
        }
 
    }
 
    /**
     * Function to logout from twitter
     * It will just clear the application shared preferences
     * */
    private void logoutFromTwitter() {
        // Clear the shared preferences
        Editor e = mSharedPreferences.edit();
        e.remove(PREF_KEY_OAUTH_TOKEN);
        e.remove(PREF_KEY_OAUTH_SECRET);
        e.remove(PREF_KEY_TWITTER_LOGIN);
        e.commit();
 
        // After this take the appropriate action
        // I am showing the hiding/showing buttons again
        // You might not needed this code
        btnLogoutTwitter.setVisibility(View.GONE);
        btnUpdateStatus.setVisibility(View.GONE);
        txtUpdate.setVisibility(View.GONE);
        lblUpdate.setVisibility(View.GONE);
        lblUserName.setText("");
        lblUserName.setVisibility(View.GONE);
 
        btnLoginTwitter.setVisibility(View.VISIBLE);
    }
 
    /**
     * Check user already logged in your application using twitter Login flag is
     * fetched from Shared Preferences
     * */
    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
    }
 
    protected void onResume() {
        super.onResume();
    }
 
}







































