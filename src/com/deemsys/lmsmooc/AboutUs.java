package com.deemsys.lmsmooc;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
public class AboutUs extends Fragment {

	String accesstokeny, accesstokensecret;
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

	private static SharedPreferences mSharedPreferences;
	private ConnectionDetector cd;
	TextView termsofuse,privacy,whylearnterst,emailcompose;

	public AboutUs() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.aboutus, container, false);
		cd = new ConnectionDetector(getActivity());
		if (!cd.isConnectingToInternet()) {

		}
		if (android.os.Build.VERSION.SDK_INT > 8) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		follow = (Button) rootView.findViewById(R.id.follo);
		termsofuse = (TextView) rootView.findViewById(R.id.termsofuse);
		privacy = (TextView) rootView.findViewById(R.id.privacy);
		whylearnterst= (TextView) rootView.findViewById(R.id.whylearnterst);
		emailcompose= (TextView) rootView.findViewById(R.id.addrs6);
		emailcompose.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	        	sendEmail(getActivity(), new String[]{"lmssupport@deemsysinc.com"}, "Sending Email",
	                    "", "");
	        }
	    });
		termsofuse.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	        	Intent intent = new Intent(getActivity(), WebViewActivity.class);
	        	intent.putExtra("urlpassing", "http://208.109.248.89:8087/OnlineCourse/user_view_Termsofuses");
			    startActivity(intent);
	        }
	    });
		privacy.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	        	Intent intent = new Intent(getActivity(), WebViewActivity.class);
	        	intent.putExtra("urlpassing", "http://208.109.248.89:8087/OnlineCourse/user_view_PrivacyPolicy");
			    startActivity(intent);
	        }
	    });
		whylearnterst.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	        	Intent intent = new Intent(getActivity(), WebViewActivity.class);
	        	intent.putExtra("urlpassing", "http://208.109.248.89:8087/OnlineCourse/whylearnterest");
			    startActivity(intent);
	        }
	    });
		
		mSharedPreferences = getActivity().getSharedPreferences("MyPref", 0);

		follow.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), AboutTweet.class);
				startActivity(intent);

			}
		});

		
		return rootView;
	}

	public static boolean isTwitterLoggedInAlready() {
		// return twitter login status from Shared Preferences

		System.out.println("value of outh key" + PREF_KEY_OAUTH_TOKEN);
		System.out.println("value of outh key secter" + PREF_KEY_OAUTH_SECRET);
		System.out.println("is already logged in :::"
				+ mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false));
		return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);

	}
	public static void sendEmail(Context context, String[] recipientList,
            String title, String subject, String body) {
    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);    
    emailIntent.setType("plain/text");    
    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipientList);
    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);   
    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
    context.startActivity(Intent.createChooser(emailIntent, title));
}
}
