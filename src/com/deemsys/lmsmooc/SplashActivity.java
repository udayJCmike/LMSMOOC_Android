package com.deemsys.lmsmooc;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends Activity {
	
	 
	
	    
	    private static int SPLASH_TIME_OUT = 3000;
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.splash);
	        ActionBar actions = getActionBar();
	        actions.hide();
	        new Handler().postDelayed(new Runnable() {
	 
	          
	            @Override
	            public void run() {
	                
	                Intent i = new Intent(SplashActivity.this,MainActivity.class);
	                startActivity(i);
	 
	               
	                finish();
	            }
	        }, SPLASH_TIME_OUT);
	    }
	 
	}
	


