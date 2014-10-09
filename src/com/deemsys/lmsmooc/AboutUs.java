package com.deemsys.lmsmooc;


import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.support.v4.app.Fragment;
import android.text.Html;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class AboutUs extends Fragment {
	
	String accesstokeny,accesstokensecret;
    static String TWITTER_CONSUMER_KEY = "q4WCEk15E9NUPlaEN5OQNdE3c";
    static String TWITTER_CONSUMER_SECRET = "dkyZBFE4KOArbvbveRxJ6YRJR2YlfQBUWlnhKAKlYzD2u7tz27";
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";
    static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
    Button follow;
    private static Twitter twitter,twitter1;
    private static RequestToken requestToken=null;
    private static SharedPreferences mSharedPreferences;
    private ConnectionDetector cd;
	public AboutUs(){}
	
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
	{
		
        View rootView = inflater.inflate(R.layout.aboutus, container, false);
        cd = new ConnectionDetector(getActivity());
        if (!cd.isConnectingToInternet()) 
        {
           
        }
        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        follow = (Button)rootView.findViewById(R.id.follo);
        mSharedPreferences = getActivity().getSharedPreferences("MyPref", 0);
        
        follow.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View arg0) {
              
                loginToTwitter();
            }
        });
        
        
        
        
        
        
        if (!isTwitterLoggedInAlready()) {
            Uri uri = getActivity().getIntent().getData();
            if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
              
                String verifier = uri
                        .getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);
                    System.out.println("verifier value::"+verifier);
                try {
                   
                    AccessToken accessToken = twitter.getOAuthAccessToken(
                            requestToken, verifier);
 
                   
                    Editor e = mSharedPreferences.edit();
 
                    System.out.println("access token value::"+accessToken.getToken());
                    e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
                    e.putString(PREF_KEY_OAUTH_SECRET,
                            accessToken.getTokenSecret());
                 
                    e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
                    e.commit(); 
                    String access_token = mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, "");
                  
                    String access_token_secret = mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, "");
                  //  Log.e("Twitter OAuth Token", "> " + accessToken.getToken());
                    ConfigurationBuilder cb = new ConfigurationBuilder();
             	    cb.setDebugEnabled(true)
             	    .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
             	    .setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET)
             	    .setOAuthAccessToken(mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, ""))
             	    .setOAuthAccessTokenSecret(mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, ""));
             	    TwitterFactory tf = new TwitterFactory(cb.build());
             	    twitter = tf.getInstance();                 

             	    try {
        String screenName="imansvijay";
        System.out.println("in create friendship");
             	        twitter.createFriendship(screenName);

             	    } catch (Exception e1) {

             	        e1.printStackTrace();
             	    }
                   
                   
                } catch (Exception e) {
                    // Check log for login errors
                    Log.e("Twitter Login Error", "> " + e.getMessage());
                }
            }
        }
        else
        {
	    	 ConfigurationBuilder cb = new ConfigurationBuilder();
	     	    cb.setDebugEnabled(true)
	     	    .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
	     	    .setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET)
	     	    .setOAuthAccessToken(mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, ""))
	     	    .setOAuthAccessTokenSecret(mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, ""));
	     	    TwitterFactory tf = new TwitterFactory(cb.build());
	     	    twitter = tf.getInstance();                 

	     	    try {
	String screenName="imansvijay";
	System.out.println("in create friendship");
	     	        twitter.createFriendship(screenName);

	     	    } catch (Exception e1) {

	     	        e1.printStackTrace();
	     	    }

        }
    
					  return rootView;
	}
	
	 private void loginToTwitter() {
	       new TwitterTestAsync().execute();
	       
	    }

	 private class TwitterTestAsync extends AsyncTask<Void, Void, Void>{

		    @Override
		    protected Void doInBackground(Void... params) {
		        // TODO Auto-generated method stub
	System.out.println("value of isTwitterLoggedInAlready::"+isTwitterLoggedInAlready());
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
		                startActivity(new Intent(Intent.ACTION_VIEW, Uri
		                         .parse(requestToken.getAuthenticationURL())));
		             } catch (TwitterException e) {
		                 e.printStackTrace();
		             }
		    	 }
		             else
		             {
		     	    	 ConfigurationBuilder cb = new ConfigurationBuilder();
		     	     	    cb.setDebugEnabled(true)
		     	     	    .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
		     	     	    .setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET)
		     	     	    .setOAuthAccessToken(mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, ""))
		     	     	    .setOAuthAccessTokenSecret(mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, ""));
		     	     	    TwitterFactory tf = new TwitterFactory(cb.build());
		     	     	    twitter = tf.getInstance();                 

		     	     	    try {
		     	String screenName="imansvijay";
		     	System.out.println("in create friendship");
		     	     	        twitter.createFriendship(screenName);

		     	     	    } catch (Exception e1) {

		     	     	        e1.printStackTrace();
		     	     	    }

		             }
		         
		    	

		        return null;
		    }
		    
		} 
	 private boolean isTwitterLoggedInAlready() {
	        // return twitter login status from Shared Preferences
	    	
	    
	    	
	    	System.out.println("value of outh key"+PREF_KEY_OAUTH_TOKEN);
	    	System.out.println("value of outh key secter"+PREF_KEY_OAUTH_SECRET);
	    	System.out.println("is already logged in :::"+mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN,false));
	        return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
	        
	        
	        
	    }
    
	 
	
}
