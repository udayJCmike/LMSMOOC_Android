package com.deemsys.lmsmooc;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;




import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;



public class LoginActivity extends Activity  {
	 EditText usname,paswd;
	 String successL;
	 Button signin,forgetpass;
	 public static String loginurl,avatarurl;
	 final Context context=this;
	 Boolean isInternetPresent = false;
	 ConnectionDetector cd;
	 public ProgressDialog pDialog;
	 private static final String TAG_SUCCESS = "success";
	 private static final String TAG_USERNAME = "username";
	 private static final String TAG_PASSWORD = "password";
	 private static final String TAG_ROLE = "role";
	 private static final String TAG_ENABLED= "enabled";
	 private static final String TAG_SRESL= "serviceresponse";
	 private static final String TAG_STUDENT_ID= "student_id";
	 private static final String TAG_FIRSTNAME= "firstname";
	 private static final String TAG_LASTNAME= "lastname";
	 private static final String TAG_EMAIL= "email";
	 private static final String TAG_INTERESTED_IN= "interested_in";
	 private static final String TAG_GENDER= "gender";
	 private static final String TAG_AVATAR= "avatar";
	 private static final String TAG_LOGINS= "logins";
	 private static final String TAG_GENCODE= "gencode";
	 private static final String TAG_AVATAR_URL= "avatar_url";
	 String username,password,role,enabled,serviceresponse,student_id;
	static String avatar_url;
	static String firstname;
	String lastname;
	String email;
	String interested_in;
	String gender;
	static String avatar;
	String logins;
	String gencode;	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.login); 
	        LinearLayout layout = (LinearLayout) findViewById(R.id.loginlayout);
	        loginurl=Config.ServerUrl+"Login.php?service=login";
	        avatarurl=Config.ServerUrl+"Login.php?service=avatar";
	        cd = new ConnectionDetector(getApplicationContext());
	        usname= (EditText) findViewById(R.id.uname);
	        paswd = (EditText) findViewById(R.id.pswd);
	        signin = (Button) findViewById(R.id.signin);
	        forgetpass = (Button) findViewById(R.id.forgetpasword);
	        ActionBar actions = getActionBar();
	        actions.setTitle(Html.fromHtml("<font color='#000000'>Login</font>"));
	        actions.setDisplayHomeAsUpEnabled(true);
	        actions.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
	        layout.setOnTouchListener(new OnTouchListener()
 	        {
 	            @Override
 	            public boolean onTouch(View view, MotionEvent ev)
 	            {
 	                hideKeyboard(view);
 	                return false;
 	            }

 				
 	        });
	        signin.setOnClickListener(new OnClickListener() {
	     	   
				@SuppressWarnings({ "deprecation", "deprecation", "deprecation" })
				@Override
				public void onClick(View arg0) {
					isInternetPresent = cd.isConnectingToInternet();
				System.out.println("signin clciked");
				 String username=usname.getText().toString();
        		 String password=paswd.getText().toString();
        		
        		if(!username.equals("")&&!password.equals(""))
        		{
        			
        	     
        			if(isInternetPresent)
        			{
        				System.out.println(username);
        				System.out.println(password);
        				
        				System.out.println("inside attempt login");
        				new AttemptLogin().execute();

        			
        			}
        		else
        		{
        			AlertDialog alertDialog = new AlertDialog.Builder(
    						LoginActivity.this).create();

    				alertDialog.setTitle("INFO!");

    				alertDialog.setMessage("No network connection.");

    				alertDialog.setIcon(R.drawable.delete);
    				
    				alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

    							public void onClick(final DialogInterface dialog,
    									final int which) {
    								
    							}
    						});

    				
    				alertDialog.show();
        			
        		}
        		}
        		else if(!password.equalsIgnoreCase(""))
        		{
        			AlertDialog alertDialog = new AlertDialog.Builder(
    						LoginActivity.this).create();

    				
    				alertDialog.setTitle("INFO!");

    				
    				alertDialog.setMessage("Please enter username.");

    				
    				alertDialog.setIcon(R.drawable.delete);
    				

    				
    				alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

    							public void onClick(final DialogInterface dialog,
    									final int which) {
    								
    							}
    						});

    				// Showing Alert Message
    				alertDialog.show();
        			
        			
        		}
        		else if(!username.equalsIgnoreCase(""))
        		{
        			AlertDialog alertDialog = new AlertDialog.Builder(
    						LoginActivity.this).create();

    				
    				alertDialog.setTitle("INFO!");

    				
    				alertDialog.setMessage("Please enter password.");

    				
    				alertDialog.setIcon(R.drawable.delete);
    				

    				
    				alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

    							public void onClick(final DialogInterface dialog,
    									final int which) {
    								
    							
    							}
    						});

    				
    				alertDialog.show();

              
        		}
				
    			else
    			{
    				AlertDialog alertDialog = new AlertDialog.Builder(
    						LoginActivity.this).create();

    				
    				alertDialog.setTitle("INFO!");

    				
    				alertDialog.setMessage("Enter login credentials.");

    				
    				alertDialog.setIcon(R.drawable.delete);
    				

    				alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

    							public void onClick(final DialogInterface dialog,
    									final int which) {
    								
    								
    							}
    						});

    				
    				alertDialog.show();

    			
    			  }
        	}
        	

				

			});
	       forgetpass.setOnClickListener(new OnClickListener() {
	    	   
				@Override
				public void onClick(View arg0) {

			
				String url = "http://208.109.248.89:8085/OnlineCourse/Student/signup";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
				}

			});
	 }
	 
	 class Avatarfetch extends AsyncTask<String, String, String> {
	    	@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	          
	        }
	    	@Override
			protected String doInBackground(String... params)
	    	{
	    		List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	             
	            
	             params1.add(new BasicNameValuePair("id", "1"));
	    		JsonParser jLogin = new JsonParser();
	             System.out.println(usname.getText().toString());
	             System.out.println( paswd.getText().toString());
	             JSONObject json = jLogin.makeHttpRequest(avatarurl,"POST", params1);
                System.out.println("value for json::"+json);
	             if(json!=null)
	             {
	                 try
	                 {
	                	 if(json != null)
	                	 {
	                	 System.out.println("json value::"+json);
	                	
	                	 JSONObject jUser = json.getJSONObject(TAG_SRESL);
	                	 successL = jUser.getString(TAG_SUCCESS);
	                	 avatar_url = jUser.getString(TAG_AVATAR_URL );
	                	 System.out.println("avatar url in second async"+avatar_url);
	                	 }
		                	
		                }
		                 
		                 catch(JSONException e)
		                 {
		                	 e.printStackTrace();
		                	 
		                 }
		              }
		             else{
		                	 
		            	 successL ="No"; 
			    			  }
				return null; 
	    	}
	    	@Override
			 protected void onPostExecute(String file_url) {
		    	   super.onPostExecute(file_url);
		    pDialog.dismiss();	
		    Intent intentSignUP=new Intent(getApplicationContext(),ProfileActivity.class);
			startActivity(intentSignUP);
	    	}
	 }

	 class AttemptLogin extends AsyncTask<String, String, String> {
	    	@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(LoginActivity.this);
	            pDialog.setMessage("Please wait...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();

	        }

			@Override
			protected String doInBackground(String... params) {
				
				 List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	             
	             params1.add(new BasicNameValuePair("username", usname.getText().toString()));
	             params1.add(new BasicNameValuePair("password", paswd.getText().toString()));

	             JsonParser jLogin = new JsonParser();
	             System.out.println(usname.getText().toString());
	             System.out.println( paswd.getText().toString());
	             JSONObject json = jLogin.makeHttpRequest(loginurl,"POST", params1);
                 System.out.println("value for json::"+json);
	             if(json!=null)
	             {
	                 try
	                 {
	                	 if(json != null)
	                	 {
	                	 System.out.println("json value::"+json);
	                	
	                	 JSONObject jUser = json.getJSONObject(TAG_SRESL);
	                	 successL = jUser.getString(TAG_SUCCESS);
	                	 username = jUser.getString(TAG_USERNAME );
	                	 password = jUser.getString(TAG_PASSWORD);
	                	 firstname= jUser.getString(TAG_FIRSTNAME);
	                	 lastname= jUser.getString(TAG_LASTNAME);
	                	 email=jUser.getString(TAG_EMAIL);
	                	 interested_in= jUser.getString(TAG_INTERESTED_IN);
	                	 gender = jUser.getString(TAG_GENDER );
	                	 avatar = jUser.getString(TAG_AVATAR);
	                	 logins = jUser.getString(TAG_LOGINS );
	                	 gencode = jUser.getString(TAG_GENCODE);
	                	 role=jUser.getString(TAG_ROLE);
	                	 enabled=jUser.getString(TAG_ENABLED);
	                	 student_id=jUser.getString(TAG_STUDENT_ID);
	                	 System.out.println("username value:::"+username);
	                	 System.out.println("password value::"+password);
	                	 System.out.println("role value"+role);
	                	
	                	 }
	                	
	                }
	                 
	                 catch(JSONException e)
	                 {
	                	 e.printStackTrace();
	                	 
	                 }
	              }
	             else{
	                	 
	            	 successL ="No"; 
		    			  } 
	                	
	                 
	    			
	    			return null;
	    		}
			@SuppressWarnings("deprecation")
			@Override
			 protected void onPostExecute(String file_url) {
		    	   super.onPostExecute(file_url);
		        System.out.println("in post execute");
		    	   pDialog.dismiss();
		          if(JsonParser.jss.equals("empty"))
		          {
		       	   System.out.println("json null value");
		       	AlertDialog alertDialog = new AlertDialog.Builder(
						LoginActivity.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("INFO!");

				// Setting Dialog Message
				alertDialog.setMessage("Server not connected.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);
				

				// Setting OK Button
				alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {
								// Write your code here to execute after dialog
								// closed
								
							}
						});

				// Showing Alert Message
				alertDialog.show();
		    
						  pDialog.dismiss();
		          }
		          else if(successL.equalsIgnoreCase("No")){
		        	  AlertDialog alertDialog = new AlertDialog.Builder(
	    						LoginActivity.this).create();

	    				// Setting Dialog Title
	    				alertDialog.setTitle("INFO!");

	    				// Setting Dialog Message
	    				alertDialog.setMessage("Invalid username or password.");

	    				// Setting Icon to Dialog
	    				alertDialog.setIcon(R.drawable.delete);
	    				

	    				// Setting OK Button
	    				alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

	    							public void onClick(final DialogInterface dialog,
	    									final int which) {
	    								// Write your code here to execute after dialog
	    								// closed
	    								usname.setText("");
	    	                    		paswd.setText("");
	    	                                dialog.dismiss();
	    							}
	    						});

	    				// Showing Alert Message
	    				alertDialog.show();
		      
		  			  pDialog.dismiss();
		           }
		          else
		          {
		        	  new Avatarfetch().execute();
		        	 
		          }
		     

			
			 }

	 }
	 protected void hideKeyboard(View view)
	 {
	     InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	     in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	 }
	 
}
